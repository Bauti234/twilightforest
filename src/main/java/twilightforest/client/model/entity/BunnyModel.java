// Date: 4/28/2012 9:36:32 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package twilightforest.client.model.entity;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import twilightforest.client.state.DwarfRabbitRenderState;

public class BunnyModel extends QuadrupedModel<DwarfRabbitRenderState> {

	public BunnyModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition create() {
		MeshDefinition meshdefinition = QuadrupedModel.createBodyMesh(1, CubeDeformation.NONE);
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(0, 8)
				.addBox(-2.0F, -1.0F, -2.0F, 4.0F, 3.0F, 5.0F)
				.texOffs(0, 18) // Tail
				.addBox(-1.0F, -2.0F, 3.0F, 2.0F, 2.0F, 2.0F),
			PartPose.offset(0.0F, 21.0F, 0.0F));

		partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
				.texOffs(0, 16)
				.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
			PartPose.offset(-2.0F, 23.0F, 2.0F));

		partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
				.texOffs(0, 16)
				.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
			PartPose.offset(1.0F, 23.0F, 2.0F));

		partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
				.texOffs(0, 16)
				.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
			PartPose.offset(-2.0F, 23.0F, -2.0F));

		partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
				.texOffs(0, 16)
				.addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
			PartPose.offset(1.0F, 23.0F, -2.0F));

		partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-2.0F, -4.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.1F))
				.texOffs(16, 0)
				.addBox(-2.5F, -8.0F, -0.5F, 2.0F, 4.0F, 1.0F)
				.texOffs(16, 0)
				.addBox(0.5F, -8.0F, -0.5F, 2.0F, 4.0F, 1.0F),
			PartPose.offset(0.0F, 22.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
}
