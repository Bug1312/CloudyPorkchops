package com.bug1312.cloudyporkchops.client.render.entity;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.bug1312.cloudyporkchops.common.entity.ExitDeliveratorPortal;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExitDeliveratorPortalRenderer <T extends ExitDeliveratorPortal> extends EntityRenderer<T> {

	private static final Random RANDOM = new Random(31100L);
	private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> { return RenderType.endPortal(p_228882_0_ + 1); }).collect(ImmutableList.toImmutableList());
		
	public ExitDeliveratorPortalRenderer(EntityRendererManager manager) {
		super(manager);
	}
	
	@Override public ResourceLocation getTextureLocation(T entity) { return null; }

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLightIn) {
		RANDOM.setSeed(31100L);
		Matrix4f matrix4f = stack.last().pose();
		this.renderPortal(0.15F, matrix4f, buffer.getBuffer(RENDER_TYPES.get(0)));
		for (int j = 1; j < 15; ++j) this.renderPortal(2.0F / (18 - j), matrix4f, buffer.getBuffer(RENDER_TYPES.get(j)));

		super.render(entity, entityYaw, partialTicks, stack, buffer, packedLightIn);
	}
	
	private void renderPortal(float u_0, Matrix4f stack, IVertexBuilder builder) {
		float f0 = (RANDOM.nextFloat() * 0.5F + 0.1F) * u_0;
		float f1 = (RANDOM.nextFloat() * 0.5F + 0.4F) * u_0;
		float f2 = (RANDOM.nextFloat() * 0.5F + 0.5F) * u_0;

		this.renderFace(stack, builder, -0.5F,  0.5F, 1, -0.5F, 0.5F, f0, f1, f2, Direction.UP);
		this.renderFace(stack, builder,  0.5F, -0.5F, 1, -0.5F, 0.5F, f0, f1, f2, Direction.DOWN);
	}

	private void renderFace(Matrix4f stack, IVertexBuilder builder, float left, float right, float height, float close, float far, float u_0, float u_1, float u_2, Direction direction) {
		builder.vertex(stack, left,  height, far  ).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, right, height, far  ).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, right, height, close).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, left,  height, close).color(u_0, u_1, u_2, 1.0F).endVertex();
	}
}
