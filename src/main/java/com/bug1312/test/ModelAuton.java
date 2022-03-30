package com.bug1312.test;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.javajson.IModelPartReloader;
import com.bug1312.javajson.JSONModel;
import com.bug1312.javajson.ModelLoader;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class ModelAuton<T extends AutonEntity> extends EntityModel<T> implements IModelPartReloader {

	private static JSONModel model = ModelLoader.loadModel(new ResourceLocation(CloudyPorkchops.MODID, "models/test2.json"));

	public ModelAuton(float modelSize) {
		super();
		registerJavaJSON();
	}

	@Override
	public void renderToBuffer(MatrixStack p_1, IVertexBuilder p_2, int p_3, int p_4, float p_5, float p_6, float p_7,
			float p_8) {
		if (model != null)
			model.getModelData().getModel().renderToBuffer(p_1, p_2, p_3, p_4, p_5, p_6, p_7, p_8);

	}

	@Override
	public void setupAnim(T p_1, float p_2, float p_3, float p_4, float p_5, float p_6) {
	}

}
