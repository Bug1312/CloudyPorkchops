package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.bug1312.cloudyporkchops.util.SprayEntityHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

@Mixin(LayerRenderer.class)
public class LayerRendererMixin<T extends Entity, M extends EntityModel<T>> {

	@Inject(at = @At("HEAD"), cancellable = true, method = "Lnet/minecraft/client/renderer/entity/layers/LayerRenderer;renderColoredCutoutModel(Lnet/minecraft/client/renderer/entity/model/EntityModel;Lnet/minecraft/util/ResourceLocation;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/entity/LivingEntity;FFF)V")
	private static <T extends LivingEntity> void renderColoredCutoutModel(EntityModel<T> m, ResourceLocation rl, MatrixStack s, IRenderTypeBuffer bf, int n_0, T entity, float n_1, float n_2, float n_3, CallbackInfo info) {
		if (SprayEntityHelper.isEntitySprayedOn(entity)) {
			IVertexBuilder ivertexbuilder = bf.getBuffer(RenderType.entityCutoutNoCull(SprayEntityHelper.texture));
			m.renderToBuffer(s, ivertexbuilder, n_0, LivingRenderer.getOverlayCoords(entity, 0.0F), n_1, n_2, n_3, 1.0F);
			info.cancel();
		}
	}

}
