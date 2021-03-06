package com.bug1312.cloudyporkchops.client.event;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.bug1312.cloudyporkchops.client.init.CloudyEntityRenders;
import com.bug1312.cloudyporkchops.client.init.CloudyOverlays;
import com.bug1312.cloudyporkchops.client.init.CloudyTileRenders;
import com.bug1312.cloudyporkchops.client.render.Item3DRendering;
import com.bug1312.cloudyporkchops.client.render.Item3DRendering.ItemRenderInfo;
import com.bug1312.cloudyporkchops.client.render.Item3DRendering.ItemRenderInfo.OtherModel;
import com.bug1312.cloudyporkchops.client.render.entity.ExitDeliveratorPortalRenderer;
import com.bug1312.cloudyporkchops.common.init.CloudyBlocks;
import com.bug1312.cloudyporkchops.common.init.CloudyEntities;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.javajson.JavaJSONModel;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientModEvents {
	
	@SubscribeEvent
	public static void renderSetup(FMLClientSetupEvent event) {
		CloudyTileRenders.init();
		CloudyEntityRenders.init();
		CloudyOverlays.init(); 
		
		RenderingRegistry.registerEntityRenderingHandler(CloudyEntities.EXIT_PORTAL.get(), ExitDeliveratorPortalRenderer::new);
		
		RenderTypeLookup.setRenderLayer(CloudyBlocks.SPRAY_ON_SIDE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(CloudyBlocks.SPRAY_ON_FULL.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(CloudyBlocks.GROCERY_DELIVERATOR.get(), RenderType.cutout());
	}
	
	@SubscribeEvent @SuppressWarnings("deprecation")
	public static void modelBakeEvent(ModelBakeEvent event) {
		Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();

		for (ItemRenderInfo renderInfo : Item3DRendering.getRenders()) {
			IBakedModel baseModel = modelRegistry.get(renderInfo.getBaseLocation());

			for (OtherModel otherModel : renderInfo.getTransforms().values()) {
				otherModel.setModel(modelRegistry.get(otherModel.getLocation()));
				modelRegistry.put(otherModel.getLocation(), otherModel.getModel());
			}
						
			IBakedModel bakedModel = new IBakedModel() {
				@Override
				public IBakedModel handlePerspective(ItemCameraTransforms.TransformType transformType, MatrixStack mat) {					
					IBakedModel model = renderInfo.getTransforms().get(transformType).getModel();
					if (model == null) model = baseModel;

					return ForgeHooksClient.handlePerspective(model, transformType, mat);
				}
				
				@Override public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) { return baseModel.getQuads(state, side, rand); }
				@Override public boolean useAmbientOcclusion() { return baseModel.useAmbientOcclusion(); }
				@Override public boolean isGui3d() { return baseModel.isGui3d(); }
				@Override public boolean isCustomRenderer() { return baseModel.isCustomRenderer(); }
				@Override public TextureAtlasSprite getParticleIcon() { return baseModel.getParticleIcon(); }
				@Override public ItemOverrideList getOverrides() { return baseModel.getOverrides(); }
				@Override public boolean usesBlockLight() { return false; }
			};
			
			modelRegistry.put(renderInfo.getBaseLocation(), bakedModel);
		}
	}

}
