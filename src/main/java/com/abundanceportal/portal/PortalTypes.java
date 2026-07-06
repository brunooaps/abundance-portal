package com.abundanceportal.portal;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import com.abundanceportal.AbundancePortal;

public final class PortalTypes {
	public static final EntityType<PortalRingEntity> PORTAL_RING = register();

	private PortalTypes() {
	}

	// Called from AbundancePortal.onInitialize() to force this class (and the registration above) to load.
	public static void bootstrap() {
	}

	private static EntityType<PortalRingEntity> register() {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, AbundancePortal.id("portal_ring"));
		return Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			key,
			EntityType.Builder.<PortalRingEntity>of(PortalRingEntity::new, MobCategory.MISC)
				.noLootTable()
				.noSave()
				.sized(6.0F, 6.0F)
				.clientTrackingRange(20)
				.updateInterval(Integer.MAX_VALUE)
				.build(key)
		);
	}
}
