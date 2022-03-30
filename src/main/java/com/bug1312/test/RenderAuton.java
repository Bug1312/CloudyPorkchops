package com.bug1312.test;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderAuton extends MobRenderer<AutonEntity, ModelAuton<AutonEntity>> {

	public RenderAuton(EntityRendererManager renderManager) {
		super(renderManager, new ModelAuton<>(1.0f), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(AutonEntity p_110775_1_) {
		return null;
	}

}
