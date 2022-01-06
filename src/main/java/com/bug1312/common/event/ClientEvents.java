package com.bug1312.common.event;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.bug1312.client.render.Item3DRendering;
import com.bug1312.client.render.Item3DRendering.ItemRenderInfo;
import com.bug1312.client.render.Item3DRendering.ItemRenderInfo.OtherModel;
import com.bug1312.main.CloudyPorkchops;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CloudyPorkchops.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

	@SubscribeEvent
	@SuppressWarnings("deprecation")
	public static void modelBakeEvent(ModelBakeEvent event) {
		Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();

		for (ItemRenderInfo renderInfo : Item3DRendering.getRenders()) {
			IBakedModel baseItem = modelRegistry.get(renderInfo.getBaseLocation());

			for (OtherModel otherModel : renderInfo.getTransforms().values()) {
				otherModel.setModel(modelRegistry.get(otherModel.getLocation()));
				modelRegistry.put(otherModel.getLocation(), otherModel.getModel());
			}

			IBakedModel bakedModel = new IBakedModel() {
				@Override
				public IBakedModel handlePerspective(ItemCameraTransforms.TransformType transformType, MatrixStack mat) {
					return ForgeHooksClient.handlePerspective(renderInfo.getTransforms().get(transformType).getModel(), transformType, mat);
				}
				
				@Override
				public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) { return baseItem.getQuads(state, side, rand); }
				@Override
				public boolean useAmbientOcclusion() { return baseItem.useAmbientOcclusion(); }
				@Override
				public boolean isGui3d() { return baseItem.isGui3d(); }
				@Override
				public boolean isCustomRenderer() { return baseItem.isCustomRenderer(); }
				@Override
				public TextureAtlasSprite getParticleIcon() { return baseItem.getParticleIcon(); }
				@Override
				public ItemOverrideList getOverrides() { return baseItem.getOverrides(); }
				@Override
				public boolean usesBlockLight() { return false; }
			};
			
			modelRegistry.put(renderInfo.getBaseLocation(), bakedModel);
		}
	}

}
