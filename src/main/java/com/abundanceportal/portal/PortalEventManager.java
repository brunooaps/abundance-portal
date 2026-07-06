package com.abundanceportal.portal;

import java.util.List;
import java.util.Random;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import com.abundanceportal.AbundancePortal;

// Drives the "sky portal" world event: periodically, near a random online player,
// lightning strikes and dark particles appear in the sky for a few seconds, then a
// creature spawns at the spot. Only one event runs at a time, world-wide (overworld only).
public final class PortalEventManager {
	private static final Random RANDOM = new Random();

	private static final int MIN_INTERVAL_TICKS = 20 * 60 * 30;
	private static final int MAX_INTERVAL_TICKS = 20 * 60 * 40;

	private static final int EFFECT_DURATION_TICKS = 20 * 12;
	private static final int PORTAL_HEIGHT = 100;
	private static final int MIN_PLAYER_DISTANCE = 40;
	private static final int MAX_PLAYER_DISTANCE = 90;
	private static final int LOCATION_SEARCH_ATTEMPTS = 20;
	private static final int LIGHTNING_STRIKE_RADIUS = 12;

	// How far around the portal particles should spread in the sky, in blocks.
	// Particles are sent with the "override limiter" flag so they reach players at this range
	// (normal particle packets are dropped past 32 blocks).
	private static final int PARTICLE_SPREAD_RADIUS = 100;

	private static long nextEventTick = -1;
	private static int effectTicksRemaining = 0;
	private static BlockPos groundPos;
	private static PortalRingEntity ringEntity;

	private PortalEventManager() {
	}

	public static void init() {
		ServerTickEvents.END_SERVER_TICK.register(PortalEventManager::onServerTick);
	}

	private static void onServerTick(MinecraftServer server) {
		ServerLevel overworld = server.overworld();

		if (effectTicksRemaining > 0) {
			tickActiveEvent(overworld);
			return;
		}

		if (nextEventTick == -1) {
			scheduleNext(overworld);
			return;
		}

		if (overworld.getGameTime() >= nextEventTick) {
			tryStartEvent(overworld);
		}
	}

	private static void scheduleNext(ServerLevel level) {
		int delay = MIN_INTERVAL_TICKS + RANDOM.nextInt(MAX_INTERVAL_TICKS - MIN_INTERVAL_TICKS);
		nextEventTick = level.getGameTime() + delay;
	}

	private static void tryStartEvent(ServerLevel level) {
		List<ServerPlayer> players = level.players();
		if (players.isEmpty()) {
			scheduleNext(level);
			return;
		}

		ServerPlayer target = players.get(RANDOM.nextInt(players.size()));
		if (!startEventNear(level, target)) {
			// Couldn't find a clear-sky spot near this player right now; just try again later.
			scheduleNext(level);
		}
	}

	// Used by the /forceportal command to trigger the event immediately, bypassing the timer.
	// If preferredTarget is null (e.g. command run from console), a random online player is used instead.
	public static boolean forceStart(ServerLevel level, ServerPlayer preferredTarget) {
		if (effectTicksRemaining > 0) {
			return false;
		}

		ServerPlayer target = preferredTarget;
		if (target == null) {
			List<ServerPlayer> players = level.players();
			if (players.isEmpty()) {
				return false;
			}

			target = players.get(RANDOM.nextInt(players.size()));
		}

		return startEventNear(level, target);
	}

	private static boolean startEventNear(ServerLevel level, ServerPlayer target) {
		BlockPos location = findOpenSkyLocation(level, target.blockPosition());
		if (location == null) {
			return false;
		}

		groundPos = location;
		effectTicksRemaining = EFFECT_DURATION_TICKS;

		BlockPos skyPos = groundPos.above(PORTAL_HEIGHT);
		ringEntity = new PortalRingEntity(level, skyPos.getX() + 0.5, skyPos.getY(), skyPos.getZ() + 0.5);
		level.addFreshEntity(ringEntity);

		AbundancePortal.LOGGER.info("Abundance Portal event starting near {} at {}", target.getName().getString(), groundPos);
		return true;
	}

	// Looks for a spot near the player's position where the ground is open to the sky
	// (no cave, mountain overhang, or player build in the way).
	private static BlockPos findOpenSkyLocation(ServerLevel level, BlockPos center) {
		for (int attempt = 0; attempt < LOCATION_SEARCH_ATTEMPTS; attempt++) {
			double angle = RANDOM.nextDouble() * Math.PI * 2;
			int distance = MIN_PLAYER_DISTANCE + RANDOM.nextInt(MAX_PLAYER_DISTANCE - MIN_PLAYER_DISTANCE);
			int x = center.getX() + (int) Math.round(Math.cos(angle) * distance);
			int z = center.getZ() + (int) Math.round(Math.sin(angle) * distance);

			if (!level.hasChunk(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z))) {
				continue;
			}

			int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
			BlockPos surfacePos = new BlockPos(x, surfaceY, z);
			if (level.canSeeSky(surfacePos.above())) {
				return surfacePos;
			}
		}

		return null;
	}

	private static void tickActiveEvent(ServerLevel level) {
		effectTicksRemaining--;

		if (effectTicksRemaining % 20 == 0 || RANDOM.nextInt(15) == 0) {
			spawnLightning(level);
		}

		spawnPortalParticles(level);

		if (effectTicksRemaining <= 0) {
			spawnGiant(level);

			if (ringEntity != null) {
				ringEntity.discard();
				ringEntity = null;
			}

			groundPos = null;
			scheduleNext(level);
		}
	}

	// Visual-only lightning near the portal: no fire, no damage, just the flash and thunder sound.
	private static void spawnLightning(ServerLevel level) {
		int x = groundPos.getX() + RANDOM.nextInt(LIGHTNING_STRIKE_RADIUS * 2 + 1) - LIGHTNING_STRIKE_RADIUS;
		int z = groundPos.getZ() + RANDOM.nextInt(LIGHTNING_STRIKE_RADIUS * 2 + 1) - LIGHTNING_STRIKE_RADIUS;
		int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
		BlockPos strikePos = new BlockPos(x, y, z);

		LightningBolt bolt = EntityTypes.LIGHTNING_BOLT.create(level, EntitySpawnReason.EVENT);
		if (bolt != null) {
			bolt.snapTo(Vec3.atBottomCenterOf(strikePos));
			bolt.setVisualOnly(true);
			level.addFreshEntity(bolt);
		}
	}

	private static void spawnPortalParticles(ServerLevel level) {
		BlockPos skyPos = groundPos.above(PORTAL_HEIGHT);

		// The portal ring model itself now provides the centerpiece visual; particles here are
		// just atmosphere scattered around and below it.

		// Scatter particles across the whole sky area around the portal, so anyone within
		// PARTICLE_SPREAD_RADIUS blocks sees something happening overhead, not just a single point.
		for (int i = 0; i < 20; i++) {
			double angle = RANDOM.nextDouble() * Math.PI * 2;
			double dist = RANDOM.nextDouble() * PARTICLE_SPREAD_RADIUS;
			double px = skyPos.getX() + 0.5 + Math.cos(angle) * dist;
			double pz = skyPos.getZ() + 0.5 + Math.sin(angle) * dist;
			double py = skyPos.getY() + RANDOM.nextDouble() * 20 - 10;
			sendFarParticles(level, ParticleTypes.PORTAL, px, py, pz, 10, 2.0, 2.0, 2.0, 0.1);
		}

		// A dense column between the ground and the portal, so it reads as a beam from a distance.
		for (int i = 0; i < 15; i++) {
			int y = groundPos.getY() + RANDOM.nextInt(PORTAL_HEIGHT);
			sendFarParticles(level, ParticleTypes.SOUL, groundPos.getX() + 0.5, y, groundPos.getZ() + 0.5, 6, 1.5, 1.5, 1.5, 0.02);
		}
	}

	// Sends particles ignoring the vanilla 32-block visibility limit, so they're seen from up to
	// 512 blocks away (needed since the portal itself sits PORTAL_HEIGHT blocks above the ground).
	private static <T extends ParticleOptions> void sendFarParticles(
		ServerLevel level, T particle, double x, double y, double z, int count, double xDist, double yDist, double zDist, double speed
	) {
		level.sendParticles(particle, true, true, x, y, z, count, xDist, yDist, zDist, speed);
	}

	private static void spawnGiant(ServerLevel level) {
		Giant giant = EntityTypes.GIANT.create(level, EntitySpawnReason.EVENT);
		if (giant == null) {
			return;
		}

		giant.snapTo(Vec3.atBottomCenterOf(groundPos));
		giant.finalizeSpawn(level, level.getCurrentDifficultyAt(giant.blockPosition()), EntitySpawnReason.EVENT, null);
		level.addFreshEntity(giant);

		AbundancePortal.LOGGER.info("Abundance Portal spawned a Giant at {}", groundPos);
	}
}
