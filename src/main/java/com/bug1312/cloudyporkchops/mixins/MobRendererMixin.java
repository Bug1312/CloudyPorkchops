package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BatModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.MobEntity;

@Mixin(LivingRenderer.class)
public class MobRendererMixin<T extends MobEntity, M extends EntityModel<T>> {
	@SuppressWarnings("unchecked")
	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/renderer/entity/LivingRenderer;getModel()Lnet/minecraft/client/renderer/entity/model/EntityModel;", cancellable= true)
	private void getModel(CallbackInfoReturnable<M> info) {
		if(info.getReturnValue() != null)
		System.out.println(info.getReturnValue().toString());
//		info.setReturnValue((M) new BatModel());
	}
	
//	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/renderer/entity/LivingRenderer;getTextureLocation(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/ResourceLocation;", cancellable = true)
//	private void getTextureLocation(CallbackInfoReturnable<ResourceLocation> info) {
//		info.setReturnValue(new ResourceLocation("textures/entity/cow.png"));
//	}
}
