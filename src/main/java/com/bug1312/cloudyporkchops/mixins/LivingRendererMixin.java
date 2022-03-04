package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import com.bug1312.cloudyporkchops.util.SprayEntityHelper;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

@Mixin(LivingRenderer.class)
public class LivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
	@ModifyVariable(at = @At("STORE"), ordinal = 0, method = "Lnet/minecraft/client/renderer/entity/LivingRenderer;getRenderType(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;")
	private ResourceLocation swapTexture(ResourceLocation def, T entity) {
		if (SprayEntityHelper.isEntitySprayedOn(entity)) return SprayEntityHelper.getTexture(def);
		return def;
	}

}
