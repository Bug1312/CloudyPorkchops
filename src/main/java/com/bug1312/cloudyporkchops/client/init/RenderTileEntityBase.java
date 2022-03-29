package com.bug1312.cloudyporkchops.client.init;

import com.bug1312.javajson.IModelPartReloader;
import com.bug1312.javajson.ModelJSONTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;

public class RenderTileEntityBase extends TileEntityRenderer<TileEntity> implements IModelPartReloader {

	private Model modelBase;
	private ResourceLocation texture;

	public RenderTileEntityBase(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		registerJavaJSON();
	}

	public RenderTileEntityBase(TileEntityRendererDispatcher rendererDispatcherIn, ModelJSONTile model) {
		super(rendererDispatcherIn);
		this.modelBase = model;
		this.texture = model.getModelData().getModelData().getTexture();
	}

	public RenderTileEntityBase(TileEntityRendererDispatcher rendererDispatcherIn, Model model, ResourceLocation location) {
		super(rendererDispatcherIn);
		this.modelBase = model;
		this.texture = location;
	}

	@Override
	public void render(TileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.modelBase.renderType(texture));//texture.getBuffer(bufferIn, RenderType::getEntitySolid);

		float rot = 0;
			
//		if (tileEntityIn instanceof DMTileEntityBase && tileEntityIn.getBlockState().getBlock() instanceof RotatableTileEntityBase) {
//			switch (tileEntityIn.getBlockState().getValue(HorizontalBlock.FACING)) {
//				case NORTH: default: 	rot = 0; break;
//				case EAST: 				rot = 90; break;
//				case SOUTH: 			rot = 180; break;
//				case WEST: 				rot = 270; break;
//			}			
//		}
		
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.5d, 0d, 0.5d);
		matrixStackIn.scale(getScale(), getScale(), getScale());
		matrixStackIn.translate(0, 1.5d, 0d);
		matrixStackIn.mulPose(new Quaternion(180, rot, 0, true));
		modelBase.renderToBuffer(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1, 1, 1, 1);
		matrixStackIn.popPose();
	}

	public float getScale() {
		return 1;
	}

	public void setTexture(ResourceLocation texture) {
		this.texture = texture;
	}

	public void setModel(Model modelBase) {
		this.modelBase = modelBase;
	}

}
