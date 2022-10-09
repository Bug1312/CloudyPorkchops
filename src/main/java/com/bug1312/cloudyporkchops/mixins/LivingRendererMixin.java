package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.bug1312.cloudyporkchops.util.helpers.SprayEntityHelper;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public class LivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

	@ModifyVariable(at = @At("STORE"), ordinal = 0, method = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;")
	private ResourceLocation swapTexture(ResourceLocation def, T entity) {
		if (SprayEntityHelper.isEntitySprayedOn(entity)) return SprayEntityHelper.getTexture(def);
		return def;
	}

}
