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

// Adapted from a Blockbench export (8 angled segments, "bone" through "bone8", forming an
// octagonal ring around the "bone9" root). Continuous clockwise spin is applied in setupAnim
// instead of using Blockbench's baked animation, since Block/Entity model exports don't carry
// animation timing the way we want (a spin that lasts the whole event, not a fixed clip).
public class PortalRingModel extends EntityModel<EntityRenderState> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(AbundancePortal.id("portal_ring"), "main");

	// Radians per tick. Positive spins one way around Y; flip the sign if it looks
	// counter-clockwise in-game.
	private static final float SPIN_SPEED = 0.035F;

	private final ModelPart bone9;

	public PortalRingModel(ModelPart root) {
		super(root);
		this.bone9 = root.getChild("bone9");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone9 = partdefinition.addOrReplaceChild(
			"bone9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -0.3054F, 0.0F)
		);

		PartDefinition bone = bone9.addOrReplaceChild(
			"bone", CubeListBuilder.create(), PartPose.offsetAndRotation(-9.1924F, -56.0F, -4.9497F, 0.0F, -0.7854F, 0.0F)
		);
		bone.addOrReplaceChild(
			"cube_r1",
			CubeListBuilder.create().texOffs(0, 25).addBox(-19.0F, -17.0F, -3.0F, 18.0F, 16.0F, 9.0F, new CubeDeformation(0.002F)),
			PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, -0.7854F, 0.0F)
		);

		PartDefinition bone2 = bone9.addOrReplaceChild(
			"bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -56.0F, -10.0F, 0.0F, -0.7854F, 0.0F)
		);
		bone2.addOrReplaceChild(
			"cube_r2",
			CubeListBuilder.create().texOffs(0, 50).addBox(-19.0F, -17.0F, -3.0F, 18.0F, 16.0F, 9.0F, new CubeDeformation(0.003F)),
			PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, -1.5708F, 0.0F)
		);

		PartDefinition bone3 = bone9.addOrReplaceChild(
			"bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(4.9497F, -56.0F, -9.1924F, 0.0F, -0.7854F, 0.0F)
		);
		bone3.addOrReplaceChild(
			"cube_r3",
			CubeListBuilder.create().texOffs(54, 0).addBox(-19.0F, -17.0F, -3.0F, 18.0F, 16.0F, 9.0F, new CubeDeformation(0.004F)),
			PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, -2.3562F, 0.0F)
		);

		PartDefinition bone4 = bone9.addOrReplaceChild(
			"bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(10.0F, -56.0F, -3.0F, 0.0F, -0.7854F, 0.0F)
		);
		bone4.addOrReplaceChild(
			"cube_r4",
			CubeListBuilder.create().texOffs(54, 25).addBox(-19.0F, -17.0F, -3.0F, 18.0F, 16.0F, 9.0F, new CubeDeformation(0.005F)),
			PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 3.1416F, 0.0F)
		);

		PartDefinition bone5 = bone9.addOrReplaceChild(
			"bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(9.1924F, -56.0F, 4.9497F, 0.0F, -0.7854F, 0.0F)
		);
		bone5.addOrReplaceChild(
			"cube_r5",
			CubeListBuilder.create().texOffs(54, 50).addBox(-19.0F, -17.0F, -3.0F, 18.0F, 16.0F, 9.0F, new CubeDeformation(0.006F)),
			PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 2.3562F, 0.0F)
		);

		PartDefinition bone6 = bone9.addOrReplaceChild(
			"bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, -56.0F, 10.0F, 0.0F, -0.7854F, 0.0F)
		);
		bone6.addOrReplaceChild(
			"cube_r6",
			CubeListBuilder.create().texOffs(0, 75).addBox(-19.0F, -17.0F, -3.0F, 18.0F, 16.0F, 9.0F, new CubeDeformation(0.007F)),
			PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 1.5708F, 0.0F)
		);

		PartDefinition bone7 = bone9.addOrReplaceChild(
			"bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.9497F, -56.0F, 9.1924F, 0.0F, -0.7854F, 0.0F)
		);
		bone7.addOrReplaceChild(
			"cube_r7",
			CubeListBuilder.create().texOffs(54, 75).addBox(-19.0F, -17.0F, -3.0F, 18.0F, 16.0F, 9.0F, new CubeDeformation(-0.001F)),
			PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.7854F, 0.0F)
		);

		PartDefinition bone8 = bone9.addOrReplaceChild(
			"bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.0F, -56.0F, 3.0F, 0.0F, -0.7854F, 0.0F)
		);
		bone8.addOrReplaceChild(
			"cube_r8",
			CubeListBuilder.create().texOffs(0, 0).addBox(-19.0F, -17.0F, -3.0F, 18.0F, 16.0F, 9.0F, new CubeDeformation(0.001F)),
			PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F)
		);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(EntityRenderState state) {
		super.setupAnim(state);
		this.bone9.yRot += state.ageInTicks * SPIN_SPEED;
	}
}
