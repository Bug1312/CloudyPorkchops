package com.bug1312.test;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.javajson.IModelPartReloader;
import com.bug1312.javajson.JSONModel;
import com.bug1312.javajson.ModelLoader;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

public class ModelAuton <T extends AutonEntity> extends EntityModel<T> implements IModelPartReloader {

	public ModelAuton(float modelSize) {
		super();
		registerJavaJSON();
	}


	@Override
	public void renderToBuffer(MatrixStack p_225598_1_, IVertexBuilder p_225598_2_, int p_225598_3_, int p_225598_4_,
			float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_) {
		JSONModel model = ModelLoader.loadModel(new ResourceLocation(CloudyPorkchops.MODID, "models/test2.json"));
		if(model != null)
		model.getModelData().getModel().renderToBuffer(p_225598_1_, p_225598_2_, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);

	}


	@Override
	public void setupAnim(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_,
			float p_225597_6_) {
		// TODO Auto-generated method stub
		
	}

}
