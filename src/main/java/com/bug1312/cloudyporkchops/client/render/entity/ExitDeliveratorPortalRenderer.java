package com.bug1312.cloudyporkchops.client.render.entity;

import com.bug1312.cloudyporkchops.common.entity.ExitDeliveratorPortal;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class ExitDeliveratorPortalRenderer <T extends ExitDeliveratorPortal> extends EntityRenderer<T> {

	public ExitDeliveratorPortalRenderer(EntityRendererManager manager) {
		super(manager);
	}

//	@Override
//	public void render(T tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
//		RANDOM.setSeed(31100L);
//		Matrix4f matrix4f = stack.last().pose();
//		this.renderPortal(tile, 0.15F, matrix4f, buffer.getBuffer(RENDER_TYPES.get(0)), tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_AXIS));
//		for (int j = 1; j < 15; ++j)
//			this.renderPortal(tile, 2.0F / (18 - j), matrix4f, buffer.getBuffer(RENDER_TYPES.get(j)), tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_AXIS));
//	}
	
	@Override
	public ResourceLocation getTextureLocation(T tile) {
		return null;
	}

	private void renderFace(T tile, Matrix4f stack, IVertexBuilder builder, float left, float right, float bottom, float top, float close, float far, float u_0, float u_1, float u_2, Direction direction) {
		builder.vertex(stack, left,  bottom, far).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, right, bottom, close).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, right, top,    close  ).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, left,  top,    far  ).color(u_0, u_1, u_2, 1.0F).endVertex();
	}
	
}
