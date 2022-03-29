package com.bug1312.javajson;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class ModelJSONTile extends Model{
	private JSONModel model;

	public ModelJSONTile(ResourceLocation rl) {
		super(RenderType::entityCutoutNoCull);
		
		if(rl != null) model = ModelLoader.loadModel(rl);
	}	

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		model.getModelData().getModel().renderToBuffer(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	public JSONModel getModelData() {
		return model;
	}

	public ModelRenderer getPart(String partName) {
		if(getModelData() != null && getModelData().getModelData().getModel() != null) {
			return getModelData().getModelData().getModel().partsList.get(partName);
		}
		return null;
	}

	public void setModel(JSONModel model) {
		this.model = model;
	}

}
