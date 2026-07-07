package com.abundanceportal.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

import com.abundanceportal.AbundancePortal;

// Adapted from a Blockbench export ("portal v2" - revised): a static, rounder frame built from
// many small angled segments ("bone2" and "bb_main"), plus a "window" (hand-drawn spinning blue
// vortex, see gen_swirl_alloever.js) filling the opening. The ring actually renders upright
// (facing the viewer), so the window is a thin vertical plate (thin in Z) spinning around Z,
// like a coin facing the camera - not a horizontal slab like the first attempt.
//
// The frame is temporarily hidden (bone2/bb_main set invisible) while dialing in the window on
// its own, per request - flip SHOW_FRAME back on once the window looks right.
public class PortalRingModel extends EntityModel<EntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(AbundancePortal.id("portal_ring"), "main");

	private static final boolean SHOW_FRAME = false;
	private static final float SPIN_SPEED = 0.05F;

	private final ModelPart window;

	public PortalRingModel(ModelPart root) {
		super(root);
		this.window = root.getChild("window");
		root.getChild("bone2").visible = SHOW_FRAME;
		root.getChild("bb_main").visible = SHOW_FRAME;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone2 = partdefinition.addOrReplaceChild(
			"bone2",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-2.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-6.0F, 12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-6.0F, 14.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-4.0F, 16.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-4.0F, 18.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-2.0F, 19.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-1.0F, 20.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(6.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F)
		);

		bone2.addOrReplaceChild(
			"cube_r1",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-4.0F, 15.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bone2.addOrReplaceChild(
			"cube_r2",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-6.0F, 11.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bone2.addOrReplaceChild(
			"cube_r3",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-6.0F, 10.0F, 0.0F, 0.0F, 0.0F, -1.5708F)
		);

		bone2.addOrReplaceChild(
			"cube_r4",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-6.0F, 7.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bone2.addOrReplaceChild(
			"cube_r5",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-5.0F, 6.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bone2.addOrReplaceChild(
			"cube_r6",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-5.0F, 4.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bone2.addOrReplaceChild(
			"cube_r7",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-4.0F, 3.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bone2.addOrReplaceChild(
			"cube_r8",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-3.0F, 2.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bone2.addOrReplaceChild(
			"cube_r9",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bbMain = partdefinition.addOrReplaceChild(
			"bb_main",
			CubeListBuilder.create()
				.texOffs(-8, 0)
				.addBox(-5.0F, -5.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-10.0F, -8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-10.0F, -10.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(-8, 0)
				.addBox(-5.0F, -29.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-12.0F, -12.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-12.0F, -14.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-7.0F, -6.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-7.0F, -28.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-8.0F, -7.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0)
				.addBox(-8.0F, -27.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offset(0.0F, 24.0F, 0.0F)
		);

		bbMain.addOrReplaceChild(
			"cube_r10",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-12.0F, -19.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bbMain.addOrReplaceChild(
			"cube_r11",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-12.0F, -15.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bbMain.addOrReplaceChild(
			"cube_r12",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-10.0F, -11.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bbMain.addOrReplaceChild(
			"cube_r13",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-10.0F, -23.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bbMain.addOrReplaceChild(
			"cube_r14",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-11.0F, -20.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bbMain.addOrReplaceChild(
			"cube_r15",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-11.0F, -22.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bbMain.addOrReplaceChild(
			"cube_r16",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-9.0F, -26.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bbMain.addOrReplaceChild(
			"cube_r17",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-9.0F, -24.0F, 0.0F, 0.0F, 0.0F, -3.1416F)
		);

		bbMain.addOrReplaceChild(
			"cube_r18",
			CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
			PartPose.offsetAndRotation(-12.0F, -16.0F, 0.0F, 0.0F, 0.0F, -1.5708F)
		);

		// The window: a thin horizontal disc (thin in Y) filling the ring's interior, textured
		// with the hand-drawn pixelated swirl, transparent everywhere outside the circle.
		// Centered roughly in the middle of bb_main's opening.
		partdefinition.addOrReplaceChild(
			"window",
			CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -0.5F, -12.0F, 20.0F, 1.0F, 24.0F, new CubeDeformation(0.0F)),
			PartPose.offset(-3.5F, 7.0F, 0.0F)
		);

		return LayerDefinition.create(meshdefinition, 68, 26);
	}

	@Override
	public void setupAnim(EntityRenderState state) {
		super.setupAnim(state);
		this.window.yRot += state.ageInTicks * SPIN_SPEED;
	}
}
