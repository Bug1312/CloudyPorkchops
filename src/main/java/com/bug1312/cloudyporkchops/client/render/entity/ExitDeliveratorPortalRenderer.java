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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExitDeliveratorPortalRenderer <T extends ExitDeliveratorPortal> extends EntityRenderer<T> {

	private static final Random RANDOM = new Random(31100L);
//	private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> { return RenderType.endPortal(p_228882_0_ + 1); }).collect(ImmutableList.toImmutableList());
	
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
	private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

	
	public ExitDeliveratorPortalRenderer(EntityRendererManager manager) {
		super(manager);
	}
	
	public void render(T p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
	      p_225623_4_.pushPose();
	      p_225623_4_.scale(2.0F, 2.0F, 2.0F);
	      p_225623_4_.mulPose(this.entityRenderDispatcher.cameraOrientation());
	      p_225623_4_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
	      MatrixStack.Entry matrixstack$entry = p_225623_4_.last();
	      Matrix4f matrix4f = matrixstack$entry.pose();
	      Matrix3f matrix3f = matrixstack$entry.normal();
	      IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(RENDER_TYPE);
	      vertex(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 0.0F, 0, 0, 1);
	      vertex(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 1.0F, 0, 1, 1);
	      vertex(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 1.0F, 1, 1, 0);
	      vertex(ivertexbuilder, matrix4f, matrix3f, p_225623_6_, 0.0F, 1, 0, 0);
	      p_225623_4_.popPose();
	      super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
	   }

	   private static void vertex(IVertexBuilder p_229045_0_, Matrix4f p_229045_1_, Matrix3f p_229045_2_, int p_229045_3_, float p_229045_4_, int p_229045_5_, int p_229045_6_, int p_229045_7_) {
	      p_229045_0_.vertex(p_229045_1_, p_229045_4_ - 0.5F, (float)p_229045_5_ - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float)p_229045_6_, (float)p_229045_7_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229045_3_).normal(p_229045_2_, 0.0F, 1.0F, 0.0F).endVertex();
	   }

	   public ResourceLocation getTextureLocation(T p_110775_1_) {
	      return TEXTURE_LOCATION;
	   }
	
	/*@Override
	public ResourceLocation getTextureLocation(T entity) { return null; }

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int packedLightIn) {
		RANDOM.setSeed(31100L);
		Matrix4f matrix4f = stack.last().pose();
		this.renderPortal(0.15F, matrix4f, buffer.getBuffer(RENDER_TYPES.get(0)));
		for (int j = 1; j < 15; ++j) this.renderPortal(2.0F / (18 - j), matrix4f, buffer.getBuffer(RENDER_TYPES.get(j)));

		System.out.println("Rendering?");
		super.render(entity, entityYaw, partialTicks, stack, buffer, packedLightIn);
	}
	

	
	private void renderPortal(float u_0, Matrix4f stack, IVertexBuilder builder) {
		float f0 = (RANDOM.nextFloat() * 0.5F + 0.1F) * u_0;
		float f1 = (RANDOM.nextFloat() * 0.5F + 0.4F) * u_0;
		float f2 = (RANDOM.nextFloat() * 0.5F + 0.5F) * u_0;

		// Testing Side
		this.renderFace(stack, builder, 0.5F, 0.5F, 5 / 16F, 3.0F, 0.0F, 1.0F, f0, f1, f2, Direction.WEST);
		
		this.renderFace(stack, builder, 0.0F, 1.0F, 0.5F, 0.5F, 1.0F, 0.0F, f0, f1, f2, Direction.UP);
		this.renderFace(stack, builder, 1.0F, 0.0F, 0.5F, 0.5F, 0.0F, 1.0F, f0, f1, f2, Direction.DOWN);
	}

	private void renderFace(Matrix4f stack, IVertexBuilder builder, float left, float right, float bottom, float top, float close, float far, float u_0, float u_1, float u_2, Direction direction) {
		builder.vertex(stack, left,  bottom, far).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, right, bottom, close).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, right, top,    close).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, left,  top,    far  ).color(u_0, u_1, u_2, 1.0F).endVertex();
	}
	*/
}
