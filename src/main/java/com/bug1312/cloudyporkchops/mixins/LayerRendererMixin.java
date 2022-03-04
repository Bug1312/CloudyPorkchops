package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import com.bug1312.cloudyporkchops.util.SprayEntityHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

@Mixin(LayerRenderer.class)
public class LayerRendererMixin<T extends Entity, M extends EntityModel<T>> {
	
	@ModifyVariable(at = @At("STORE"), ordinal = 0, method = "Lnet/minecraft/client/renderer/entity/layers/LayerRenderer;renderColoredCutoutModel(Lnet/minecraft/client/renderer/entity/model/EntityModel;Lnet/minecraft/util/ResourceLocation;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFF)V")
	private static <T extends LivingEntity> IVertexBuilder swapTexture(IVertexBuilder def, EntityModel<T> u_0, ResourceLocation u_1, MatrixStack u_2, IRenderTypeBuffer buffer, int u_3, T entity) {
		if(SprayEntityHelper.isEntitySprayedOn(entity)) return buffer.getBuffer(RenderType.entityCutoutNoCull(SprayEntityHelper.texture));
		return def;
	}

}
