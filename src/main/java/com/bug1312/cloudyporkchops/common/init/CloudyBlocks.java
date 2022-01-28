package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.common.RegistryHandler;
import com.bug1312.cloudyporkchops.common.block.inventions.InvisibleTable;
import com.bug1312.cloudyporkchops.common.block.inventions.SprayOnBlock;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class CloudyBlocks {

	public static RegistryObject<Block> INVISIBLE_TABLE = registerBlock(new InvisibleTable(Properties.of(Material.WOOD).noOcclusion()), "invisible_table", true);
	public static RegistryObject<Block> SPRAY_ON = registerBlock(new SprayOnBlock(Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion().strength(50.0F, 1200.0F)), "elastic_biopolymer_adhesive", false);
	
	/* Register */
	public static RegistryObject<Block> registerBlock(Block block, String name, Item.Properties properties, boolean hasItem) {
		if (hasItem) {
			RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, properties));
		}
		return RegistryHandler.BLOCKS.register(name, () -> block);
	}	
	
	public static RegistryObject<Block> registerBlock(Block block, String name, boolean hasItem) {
		return registerBlock(block, name, new Item.Properties(), hasItem);
	}	
}
