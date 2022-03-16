package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.common.block.foods.Jello;
import com.bug1312.cloudyporkchops.common.block.inventions.GroceryDeliverator;
import com.bug1312.cloudyporkchops.common.block.inventions.InvisibleTable;
import com.bug1312.cloudyporkchops.common.block.inventions.SprayOnBlock;
import com.bug1312.cloudyporkchops.common.tile.inventions.GroceryDeliveratorTile;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class CloudyBlocks {

	/* Decoration */
	public static RegistryObject<Block> INVISIBLE_TABLE 	= registerBlock("invisible_table", new InvisibleTable(Properties.of(Material.WOOD).noOcclusion()), true);

	/* Foods */
	public static RegistryObject<Block> JELLO 				= registerBlock("jello_block", new Jello(Properties.of(Material.CLAY, MaterialColor.COLOR_ORANGE).speedFactor(0.4F).noOcclusion().sound(SoundType.HONEY_BLOCK)), true);
	
	/* Useful */
	public static RegistryObject<Block> GROCERY_DELIVERATOR = registerBlock("grocery_deliverator", new GroceryDeliverator(GroceryDeliveratorTile::new, Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion()), true);
	
	/* Crafting */
	public static RegistryObject<Block> SPRAY_ON_FULL 		= registerBlock("biopolymer_adhesive_block", new BreakableBlock(Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion().strength(50.0F, 1200.0F)),  false);

	/* Other */
	public static RegistryObject<Block> SPRAY_ON_SIDE		= registerBlock("biopolymer_adhesive", new SprayOnBlock(Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion().strength(50.0F, 1200.0F)),  false);

	
	/* Register Methods */
	public static RegistryObject<Block> registerBlock(String name, Block block, Item.Properties properties, boolean hasItem) {
		if (hasItem) RegistryHandler.ITEMS.register(name, () -> new BlockItem(block, properties));
		return RegistryHandler.BLOCKS.register(name, () -> block);
	}	
	
	public static RegistryObject<Block> registerBlock(String name, Block block, boolean hasItem) {
		return registerBlock(name, block, new Item.Properties(), hasItem);
	}	
}
