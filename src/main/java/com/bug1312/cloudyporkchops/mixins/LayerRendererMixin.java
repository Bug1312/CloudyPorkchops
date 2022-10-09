package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.bug1312.cloudyporkchops.util.helpers.SprayEntityHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin(RenderLayer.class)
public class LayerRendererMixin<T extends Entity, M extends EntityModel<T>> {

	@ModifyVariable(at = @At("STORE"), ordinal = 0, method = "Lnet/minecraft/client/renderer/entity/layers/RenderLayer;renderColoredCutoutModel(Lnet/minecraft/client/model/EntityModel;Lnet/minecraft/resources/ResourceLocation;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFF)V")
	private static <T extends LivingEntity> VertexConsumer swapTexture(VertexConsumer def, EntityModel<T> u_0, ResourceLocation texture, PoseStack u_2, MultiBufferSource buffer, int u_3, T entity) {
		if (SprayEntityHelper.isEntitySprayedOn(entity)) return buffer.getBuffer(RenderType.entityCutoutNoCull(SprayEntityHelper.getTexture(texture)));
		return def;
	}

}
