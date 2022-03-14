package com.bug1312.cloudyporkchops.client.render.tiles;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.bug1312.cloudyporkchops.common.tile.inventions.GroceryDeliveratorTile;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GroceryDeliveratorRenderer<T extends GroceryDeliveratorTile> extends TileEntityRenderer<T> {

	public GroceryDeliveratorRenderer(TileEntityRendererDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(T tile, float partialTicks, MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
		RANDOM.setSeed(31100L);
		Matrix4f matrix4f = stack.last().pose();
		if(tile.isActivated() && tile.getBlockState().getValue(BlockStateProperties.HALF) == Half.BOTTOM) {
			this.renderPortal(tile, 0.15F, matrix4f, buffer.getBuffer(RENDER_TYPES.get(0)), tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_AXIS));
			for (int j = 1; j < 15; ++j)
				this.renderPortal(tile, 2.0F / (18 - j), matrix4f, buffer.getBuffer(RENDER_TYPES.get(j)), tile.getBlockState().getValue(BlockStateProperties.HORIZONTAL_AXIS));
		}
	}

	public static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");
	public static final ResourceLocation END_PORTAL_LOCATION = new ResourceLocation("textures/entity/end_portal.png");
	private static final Random RANDOM = new Random(31100L);
	private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> { return RenderType.endPortal(p_228882_0_ + 1); }).collect(ImmutableList.toImmutableList());

	private void renderPortal(T tile, float u_0, Matrix4f stack, IVertexBuilder builder, Axis axis) {
		float f0 = (RANDOM.nextFloat() * 0.5F + 0.1F) * u_0;
		float f1 = (RANDOM.nextFloat() * 0.5F + 0.4F) * u_0;
		float f2 = (RANDOM.nextFloat() * 0.5F + 0.5F) * u_0;
		float height = (tile.isLargerPortal())? 37 / 16F : 2.0F;
		switch (axis) {
			case Z: default:
				this.renderFace(tile, stack, builder, 0.0F, 1.0F, 5 / 16F, height, 0.5F, 0.5F, f0, f1, f2, Direction.SOUTH);
				this.renderFace(tile, stack, builder, 1.0F, 0.0F, 5 / 16F, height, 0.5F, 0.5F, f0, f1, f2, Direction.NORTH);
				break;
			case X:
				this.renderFace(tile, stack, builder, 0.5F, 0.5F, 5 / 16F, height, 1.0F, 0.0F, f0, f1, f2, Direction.EAST);
				this.renderFace(tile, stack, builder, 0.5F, 0.5F, 5 / 16F, height, 0.0F, 1.0F, f0, f1, f2, Direction.WEST);
				break;
		}
	}

	private void renderFace(T tile, Matrix4f stack, IVertexBuilder builder, float left, float right, float bottom, float top, float close, float far, float u_0, float u_1, float u_2, Direction direction) {
		builder.vertex(stack, left,  bottom, far).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, right, bottom, close).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, right, top,    close  ).color(u_0, u_1, u_2, 1.0F).endVertex();
		builder.vertex(stack, left,  top,    far  ).color(u_0, u_1, u_2, 1.0F).endVertex();
	}

}
