package com.abundanceportal.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

import com.abundanceportal.AbundancePortal;
import com.abundanceportal.portal.PortalRingEntity;

public class PortalRingRenderer extends EntityRenderer<PortalRingEntity, EntityRenderState> {
	private static final Identifier TEXTURE = AbundancePortal.id("textures/entity/portal_ring.png");

	// The Blockbench model is modeled small; blow it up to a dramatic in-sky size.
	// Tweak this once you've seen it in-game.
	private static final float SCALE = 5.0F;

	private final PortalRingModel model;

	public PortalRingRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new PortalRingModel(context.bakeLayer(PortalRingModel.LAYER_LOCATION));
	}

	@Override
	public void submit(EntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.scale(SCALE, SCALE, SCALE);
		submitNodeCollector.submitModel(this.model, state, poseStack, TEXTURE, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
		poseStack.popPose();
		super.submit(state, poseStack, submitNodeCollector, camera);
	}

	@Override
	public EntityRenderState createRenderState() {
		return new EntityRenderState();
	}
}
