package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

@Mixin(LivingRenderer.class)
public class LivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

	@Shadow
	protected M model;
//
//	@Overwrite
//	@Nullable
//	protected RenderType getRenderType(T p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
//		ResourceLocation resourcelocation = new ResourceLocation("missing");
//		if (p_230496_3_) {
//			return RenderType.itemEntityTranslucentCull(resourcelocation);
//		} else if (p_230496_2_) {
//			return this.model.renderType(resourcelocation);
//		} else {
//			return p_230496_4_ ? RenderType.outline(resourcelocation) : null;
//		}
//	}

	// WIP; Do what I did above, just if on spray
	@Inject(at = @At("HEAD"), cancellable = true, method = "Lnet/minecraft/client/renderer/entity/LivingRenderer;getRenderType(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;")
	protected RenderType getRenderType(T p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_, CallbackInfoReturnable<RenderType> info) {

	}

}
