package com.bug1312.javajson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;


public class ModelWrapper extends Model{

	public JSONModel model;
	public List<ModelRendererWrapper> renderList = new ArrayList<ModelRendererWrapper>();
	public Map<String, ModelRendererWrapper> partsList = new HashMap<String, ModelRendererWrapper>();
	public float modelScale = 1;
	
	public ModelWrapper(int texWidth, int texHeight) {
		super(RenderType::entityCutoutNoCull);
		this.texHeight = texHeight;
		this.texWidth = texWidth;
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float alpha) {

		matrixStackIn.pushPose();
		matrixStackIn.translate(0, 1.5f, 0);
		matrixStackIn.scale(modelScale, modelScale, modelScale);
		matrixStackIn.translate(0, -1.5f, 0);
	

		
		for(int i = 0; i < renderList.size(); i++) {
			ModelRendererWrapper r = renderList.get(i);
			r.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		}
		
		matrixStackIn.popPose();
		
		if(model != null && model.getModelData() != null && model.getModelData().getLightMap() != null&&model.render_lightmap) {
			bufferIn = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.beaconBeam(model.getModelData().getLightMap(),true));//texture.getBuffer(bufferIn, RenderType::getEntitySolid);
			
			matrixStackIn.pushPose();
			matrixStackIn.translate(0, 1.5f, 0);
			matrixStackIn.scale(modelScale, modelScale, modelScale);
			matrixStackIn.translate(0, -1.5f, 0);
			for(int i = 0; i < renderList.size(); i++) {
				ModelRendererWrapper r = renderList.get(i);
				r.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

			}
			
			matrixStackIn.popPose();
		}
	}

	public ModelRendererWrapper getPart(String s) {
		if(partsList.containsKey(s)) {
			return partsList.get(s);
		}else {
			return ModelLoader.NULL_PART;
		}
	}

}
