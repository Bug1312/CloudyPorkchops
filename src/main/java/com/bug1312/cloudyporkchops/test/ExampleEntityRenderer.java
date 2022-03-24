package com.bug1312.cloudyporkchops.test;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExampleEntityRenderer extends EntityRenderer<ExampleEntity> {

	public static final ResourceLocation TEXTURE = new ResourceLocation("missing");

	public ExampleEntityRenderer(EntityRendererManager manager) {
		super(manager);
	}
	
	@Override
	public void render(ExampleEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
		System.out.println("redn");
		super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
	}

	@Override
	public ResourceLocation getTextureLocation(ExampleEntity p_110775_1_) {
		return TEXTURE;
	}
}

//public class ExampleEntityRenderer extends MobRenderer<ExampleEntity, ExampleEntityModel<ExampleEntity>> {
//
//	public static final ResourceLocation TEXTURE = new ResourceLocation("missing");
//
//	public ExampleEntityRenderer(EntityRendererManager manager) {
//		super(manager, new ExampleEntityModel<>(), 0.7f);
//	}
//	
//	@Override
//	public void render(ExampleEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_,
//			IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
//		System.out.println("redn");
//		super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
//	}
//
//	@Override
//	public ResourceLocation getTextureLocation(ExampleEntity p_110775_1_) {
//		return TEXTURE;
//	}
//}