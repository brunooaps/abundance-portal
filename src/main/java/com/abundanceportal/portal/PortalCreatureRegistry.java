package com.abundanceportal.portal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.Mob;

// Public API: the pool of creatures the sky portal event can spawn. Other mods (e.g. a future
// companion mod adding this book's original creatures) can add their own entries from their own
// initializer with:
//
//     PortalCreatureRegistry.register(MyEntityTypes.MY_CREATURE);
//
// No hard dependency on Abundance Portal's internals is needed beyond this one class.
public final class PortalCreatureRegistry {
	private static final List<EntityType<? extends Mob>> CREATURES = new ArrayList<>();

	private PortalCreatureRegistry() {
	}

	public static void register(EntityType<? extends Mob> type) {
		CREATURES.add(type);
	}

	public static List<EntityType<? extends Mob>> getAll() {
		return Collections.unmodifiableList(CREATURES);
	}

	public static EntityType<? extends Mob> pickRandom(Random random) {
		if (CREATURES.isEmpty()) {
			return null;
		}

		return CREATURES.get(random.nextInt(CREATURES.size()));
	}

	// Abundance Portal's own placeholder entries, registered the same way a third-party mod
	// would. Swap these out once the dedicated creature mod exists.
	public static void bootstrap() {
		register(EntityTypes.GIANT);
		register(EntityTypes.WARDEN);
		register(EntityTypes.WITHER);
	}
}
