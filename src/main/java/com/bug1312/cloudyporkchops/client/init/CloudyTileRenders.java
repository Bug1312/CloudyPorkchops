package com.bug1312.cloudyporkchops.client.init;

import com.bug1312.cloudyporkchops.client.render.tile.GroceryDeliveratorRenderer;
import com.bug1312.cloudyporkchops.common.init.CloudyTiles;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CloudyTileRenders {

	public static void init() {
		register(CloudyTiles.GROCERY_DELIVERATOR.get(), GroceryDeliveratorRenderer::new);
	}

	/* Register Method */
	public static <T extends BlockEntity> void register(BlockEntityType<T> tile, BlockEntityRendererProvider<T> renderer) {
		BlockEntityRenderers.register(tile, renderer);
	}

}
