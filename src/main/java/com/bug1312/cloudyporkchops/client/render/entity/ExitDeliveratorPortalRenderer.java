package com.bug1312.cloudyporkchops.client.render.entity;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.bug1312.cloudyporkchops.client.CloudyRenderTypes;
import com.bug1312.cloudyporkchops.common.entity.ExitDeliveratorPortal;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExitDeliveratorPortalRenderer <T extends ExitDeliveratorPortal> extends EntityRenderer<T> {

	private static Pair<ResourceLocation, Integer> TEXTURE  = new Pair<>(new ResourceLocation("cloudyporkchops:textures/block/grocery_deliverator_exit.png"), 60);
	
	private static final Random RANDOM = new Random(31100L);
	private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj(num -> { return RenderType.endPortal(num + 1); }).collect(ImmutableList.toImmutableList());
		
	public ExitDeliveratorPortalRenderer(EntityRendererManager manager) {
		super(manager);
	}
	
	@Override public ResourceLocation getTextureLocation(T entity) { return null; }

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLightIn) {
		RANDOM.setSeed(31100L);
		Matrix4f matrix4f = stack.last().pose();
		Matrix3f matrix3f = stack.last().normal();
		float offsetY = (entity.tickCount % TEXTURE.getSecond()) / (float) TEXTURE.getSecond();
		
		this.renderPortal(0.15F, matrix4f, buffer.getBuffer(RENDER_TYPES.get(0)));
		for (int i = 1; i < RENDER_TYPES.size() - 1; ++i) this.renderPortal(2.0F / (18 - i), matrix4f, buffer.getBuffer(RENDER_TYPES.get(i)));
		
		this.renderTexture(matrix4f, matrix3f, buffer.getBuffer(CloudyRenderTypes.deliveratorPortal(TEXTURE.getFirst(), offsetY)));
		
		super.render(entity, entityYaw, partialTicks, stack, buffer, packedLightIn);
	}
	
	private void renderPortal(float amplifier, Matrix4f matrix4, IVertexBuilder builder) {
		float r = (RANDOM.nextFloat() * 0.5F + 0.1F) * amplifier;
		float g = (RANDOM.nextFloat() * 0.5F + 0.4F) * amplifier;
		float b = (RANDOM.nextFloat() * 0.5F + 0.5F) * amplifier;

		this.renderFace(matrix4, builder, -0.5F,  0.5F, 0, -0.5F, 0.5F, r, g, b, Direction.UP);
		this.renderFace(matrix4, builder,  0.5F, -0.5F, 0, -0.5F, 0.5F, r, g, b, Direction.DOWN);
	}
	
	private void renderTexture(Matrix4f matrix4, Matrix3f matrix3, IVertexBuilder builder) {
		this.renderFace(matrix4, matrix3, builder, -0.5F, 0.5F, 0, -0.5F, 0.5F, TEXTURE.getSecond(), Direction.UP);
		this.renderFace(matrix4, matrix3, builder, 0.5F, -0.5F, 0, -0.5F, 0.5F, TEXTURE.getSecond(), Direction.DOWN);
	}

	private void renderFace(Matrix4f matrix4, IVertexBuilder builder, float left, float right, float height, float close, float far, float r, float g, float b, Direction direction) {
		builder.vertex(matrix4, left,  height, far  ).color(r, g, b, 1.0F).endVertex();
		builder.vertex(matrix4, right, height, far  ).color(r, g, b, 1.0F).endVertex();
		builder.vertex(matrix4, right, height, close).color(r, g, b, 1.0F).endVertex();
		builder.vertex(matrix4, left,  height, close).color(r, g, b, 1.0F).endVertex();
	}
	
	private void renderFace(Matrix4f matrix4, Matrix3f matrix3, IVertexBuilder builder, float left, float right, float height, float close, float far, float frames, Direction direction) {
		builder.vertex(matrix4, left,  height, far  ).color(1,1,1,1F).uv(0, 1 / frames).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0).normal(matrix3, 0, 1, 0).endVertex();
		builder.vertex(matrix4, right, height, far  ).color(1,1,1,1F).uv(1, 1 / frames).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(1).normal(matrix3, 0, 1, 0).endVertex();
		builder.vertex(matrix4, right, height, close).color(1,1,1,1F).uv(1, 0)		   .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(1).normal(matrix3, 0, 1, 0).endVertex();
		builder.vertex(matrix4, left,  height, close).color(1,1,1,1F).uv(0, 0)		   .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0).normal(matrix3, 0, 1, 0).endVertex();
	}
}
