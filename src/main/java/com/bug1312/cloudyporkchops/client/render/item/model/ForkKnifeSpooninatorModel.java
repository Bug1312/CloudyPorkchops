package com.bug1312.cloudyporkchops.client.render.item.model;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.javajson.IUseJavaJSON;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("rawtypes")
public class ForkKnifeSpooninatorModel extends BipedModel implements IUseJavaJSON {

	private BipedModel copyCat;

	public <M extends BipedModel> ForkKnifeSpooninatorModel(M model) {
		super(1);
		copyCat = (BipedModel) model;
		registerJavaJSON(new ResourceLocation(CloudyPorkchops.MODID, "/models/test.json"));
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		matrixStackIn.popPose();

		matrixStackIn.translate(0, 24/16F, 0);
		
		getJavaJSON().getPart("reference").copyFrom(copyCat.head);
		getJavaJSON().getPart("reference").x = 0.0F;
		getJavaJSON().getPart("reference").y = 0.0F;
//		getJavaJSON().getPart("reference").yRot *= -1;
//		getJavaJSON().getPart("reference").xRot *= -1;
		getJavaJSON().getPart("reference").xRot += Math.toRadians(180);
		
		getJavaJSON().getPart("reference").render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
		
		matrixStackIn.pushPose();

	}

}
