package com.bug1312.cloudyporkchops.client.init;

import java.util.function.Function;

import com.bug1312.cloudyporkchops.client.render.tile.GroceryDeliveratorRenderer;
import com.bug1312.cloudyporkchops.common.init.CloudyTiles;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class CloudyTileRenders {
	
	public static void init() {
		register(CloudyTiles.GROCERY_DELIVERATOR.get(), GroceryDeliveratorRenderer::new);
	}

	/* Register Method */
	public static <T extends TileEntity> void register(TileEntityType<T> tile, Function<? super TileEntityRendererDispatcher, ? extends TileEntityRenderer<? super T>> renderer) {
		ClientRegistry.bindTileEntityRenderer(tile, renderer);
	}

}
