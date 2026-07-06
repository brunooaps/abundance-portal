package com.abundanceportal.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import com.abundanceportal.portal.PortalTypes;

public class AbundancePortalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		ModelLayerRegistry.registerModelLayer(PortalRingModel.LAYER_LOCATION, PortalRingModel::createBodyLayer);
		EntityRendererRegistry.register(PortalTypes.PORTAL_RING, PortalRingRenderer::new);
	}
}