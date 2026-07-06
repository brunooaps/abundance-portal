package com.abundanceportal.portal;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

// Purely decorative: the spinning ring the sky portal event displays. No physics, no AI, no
// save data - PortalEventManager spawns and removes it explicitly in sync with the event.
public class PortalRingEntity extends Entity {
	public PortalRingEntity(EntityType<? extends PortalRingEntity> type, Level level) {
		super(type, level);
		this.setNoGravity(true);
		this.noPhysics = true;
	}

	public PortalRingEntity(Level level, double x, double y, double z) {
		this(PortalTypes.PORTAL_RING, level);
		this.setPos(x, y, z);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
	}
}
