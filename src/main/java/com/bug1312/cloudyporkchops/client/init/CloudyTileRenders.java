package com.bug1312.cloudyporkchops.client.init;

import java.util.function.Function;

import com.bug1312.cloudyporkchops.common.init.CloudyTiles;
import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.javajson.JSONModel;
import com.bug1312.javajson.ModelLoader;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class CloudyTileRenders {
	
	public static void init() {
//		register(CloudyTiles.GROCERY_DELIVERATOR.get(), GroceryDeliveratorRenderer::new);
		register(CloudyTiles.GROCERY_DELIVERATOR.get(), new ResourceLocation(CloudyPorkchops.MODID, "models/test.json"));
	}

	/* Register Method */
	public static <T extends TileEntity> void register(TileEntityType<T> tile, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> renderer) {
		ClientRegistry.bindTileEntityRenderer(tile, renderer);
	}
	
	public static <T extends TileEntity> void  register(TileEntityType<T> tileEntityType, ResourceLocation location) {
		JSONModel model = ModelLoader.loadModel(location);
		if(model != null) {
			Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> rendererFactory = (s -> new RenderTileEntityBase(s, model.getModelData().getModel(), model.getModelData().getTexture()));
			ClientRegistry.bindTileEntityRenderer(tileEntityType, rendererFactory);
		}
	}

}
