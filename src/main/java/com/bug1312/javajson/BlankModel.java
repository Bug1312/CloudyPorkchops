package com.bug1312.javajson;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;

public class BlankModel extends Model{

	public BlankModel() {
		super(RenderType::entityCutoutNoCull);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {
		// TODO Auto-generated method stub
		
	}

}
