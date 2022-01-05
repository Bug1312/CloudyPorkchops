	package com.bug1312.common.init;

import com.bug1312.common.RegistryHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;

public class Blocks {


	/* Register Normal Block */
	public static RegistryObject<Block> registerBlock(Block block, String name, Properties properties, boolean hasItem) {
		if (hasItem) {
			RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, properties));
		}
		return RegistryHandler.BLOCKS.register(name, () -> block);
	}
	
	/* Register a render type for transparent textures on a json model */
	public static void registerRenderType(Block block, RenderType renderType) {
		RenderTypeLookup.setRenderLayer(block, renderType);
	}

}
