package com.bug1312.cloudyporkchops.client.render.tile;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.bug1312.cloudyporkchops.client.CloudyRenderTypes;
import com.bug1312.cloudyporkchops.common.tile.invention.GroceryDeliveratorTile;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
	
@OnlyIn(Dist.CLIENT)
public class GroceryDeliveratorRenderer<T extends GroceryDeliveratorTile> extends TileEntityRenderer<T> {

	private static Pair<ResourceLocation, Integer> TEXTURE_TALL  = new Pair<>(new ResourceLocation("cloudyporkchops:textures/block/grocery_deliverator_portal_tall.png"), 92);
	private static Pair<ResourceLocation, Integer> TEXTURE_SHORT = new Pair<>(new ResourceLocation("cloudyporkchops:textures/block/grocery_deliverator_portal.png"), 82); 
	
    private static Random RANDOM = new Random(31100L);
	private static List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj(num -> { return RenderType.endPortal(num + 1); }).collect(ImmutableList.toImmutableList());
	
	public GroceryDeliveratorRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(T tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
		RANDOM.setSeed(31100L);
		Matrix4f matrix4 = stack.last().pose();
		Matrix3f matrix3 = stack.last().normal(); 
		Axis axis = tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_AXIS);
		float tallOffsetY  = (tile.getLevel().getGameTime() % TEXTURE_TALL .getSecond()) / (float) TEXTURE_TALL .getSecond();
		float shortOffsetY = (tile.getLevel().getGameTime() % TEXTURE_SHORT.getSecond()) / (float) TEXTURE_SHORT.getSecond();
		
		if(tile.isActivated()) {
			this.renderPortal(tile, 0.15F, matrix4, buffer.getBuffer(RENDER_TYPES.get(0)), tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_AXIS));
			for (int i = 1; i < RENDER_TYPES.size() - 1; ++i) this.renderPortal(tile, 2.0F / (18 - i), matrix4, buffer.getBuffer(RENDER_TYPES.get(i)), axis);
			
			IVertexBuilder textureBuilder = (tile.isLargerPortal()) ? 
					buffer.getBuffer(CloudyRenderTypes.deliveratorPortal(TEXTURE_TALL.getFirst(), tallOffsetY))
					: buffer.getBuffer(CloudyRenderTypes.deliveratorPortal(TEXTURE_SHORT.getFirst(), shortOffsetY));
			
			this.renderTexture(tile, matrix4, matrix3, textureBuilder, axis);	
		}		
		
	}
	
	private void renderPortal(T tile, float amplifier, Matrix4f matrix4, IVertexBuilder builder, Axis axis) {
		float r = (RANDOM.nextFloat() * 0.5F + 0.1F) * amplifier;
		float g = (RANDOM.nextFloat() * 0.5F + 0.4F) * amplifier;
		float b = (RANDOM.nextFloat() * 0.5F + 0.5F) * amplifier;
		float height = (tile.isLargerPortal())? 37 / 16F : 2.0F;
		
		switch (axis) {
			case Z: default:
				this.renderFace(tile, matrix4, builder, 0.0F, 1.0F, 5 / 16F, height, 0.5F, 0.5F, r, g, b, Direction.SOUTH);
				this.renderFace(tile, matrix4, builder, 1.0F, 0.0F, 5 / 16F, height, 0.5F, 0.5F, r, g, b, Direction.NORTH);
				break;
			case X:
				this.renderFace(tile, matrix4, builder, 0.5F, 0.5F, 5 / 16F, height, 1.0F, 0.0F, r, g, b, Direction.EAST);
				this.renderFace(tile, matrix4, builder, 0.5F, 0.5F, 5 / 16F, height, 0.0F, 1.0F, r, g, b, Direction.WEST);
				break;
		}
	}
	
	private void renderTexture(T tile, Matrix4f matrix4, Matrix3f matrix3, IVertexBuilder builder, Axis axis) {
		float height = (tile.isLargerPortal()) ? 37 / 16F : 2.0F;
		float frames = (tile.isLargerPortal()) ? TEXTURE_TALL.getSecond() : TEXTURE_SHORT.getSecond();
		
		switch (axis) {
			case Z: default:
				this.renderFace(tile, matrix4, matrix3, builder, 0.0F, 1.0F, 5 / 16F, height, 0.5F, 0.5F, frames, Direction.SOUTH);
				this.renderFace(tile, matrix4, matrix3, builder, 1.0F, 0.0F, 5 / 16F, height, 0.5F, 0.5F, frames, Direction.NORTH);
				break;
			case X:
				this.renderFace(tile, matrix4, matrix3, builder, 0.5F, 0.5F, 5 / 16F, height, 1.0F, 0.0F, frames, Direction.EAST);
				this.renderFace(tile, matrix4, matrix3, builder, 0.5F, 0.5F, 5 / 16F, height, 0.0F, 1.0F, frames, Direction.WEST);
		}
	}

	private void renderFace(T tile, Matrix4f stack, IVertexBuilder builder, float left, float right, float bottom, float top, float close, float far, float r, float g, float b, Direction direction) {
		builder.vertex(stack, left,  bottom, far  ).color(r, g, b, 1.0F).endVertex();
		builder.vertex(stack, right, bottom, close).color(r, g, b, 1.0F).endVertex();
		builder.vertex(stack, right, top,    close).color(r, g, b, 1.0F).endVertex();
		builder.vertex(stack, left,  top,    far  ).color(r, g, b, 1.0F).endVertex();
	}
	
	private void renderFace(T tile, Matrix4f matrix4, Matrix3f matrix3, IVertexBuilder builder, float left, float right, float bottom, float top, float close, float far, float frames, Direction direction) {
		builder.vertex(matrix4, left,  bottom, far  ).color(1,1,1,1F).uv(0, 1 / frames).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0).normal(matrix3, 0, 1, 0).endVertex();
		builder.vertex(matrix4, right, bottom, close).color(1,1,1,1F).uv(1, 1 / frames).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(1).normal(matrix3, 0, 1, 0).endVertex();
		builder.vertex(matrix4, right, top,    close).color(1,1,1,1F).uv(1, 0)		   .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(1).normal(matrix3, 0, 1, 0).endVertex();
		builder.vertex(matrix4, left,  top,    far  ).color(1,1,1,1F).uv(0, 0)		   .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(0).normal(matrix3, 0, 1, 0).endVertex();
	}

}
