package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.common.block.food.Jello;
import com.bug1312.cloudyporkchops.common.block.invention.GroceryDeliverator;
import com.bug1312.cloudyporkchops.common.block.invention.InvisibleTable;
import com.bug1312.cloudyporkchops.common.block.invention.SprayOnBlock;
import com.bug1312.cloudyporkchops.common.item.BlockItem3D;
import com.bug1312.cloudyporkchops.common.tile.invention.GroceryDeliveratorTile;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

@SuppressWarnings("unused")
public class CloudyBlocks {

	/* Decoration */
	public static RegistryObject<Block> INVISIBLE_TABLE 	= register("invisible_table", new InvisibleTable(Properties.of(Material.WOOD).noOcclusion()), true);

	/* Foods */
	public static RegistryObject<Block> JELLO 				= register("jello_block", new Jello(Properties.of(Material.CLAY, MaterialColor.COLOR_ORANGE).speedFactor(0.4F).noOcclusion().sound(SoundType.HONEY_BLOCK)), true);
	
	/* Useful */
	public static RegistryObject<Block> GROCERY_DELIVERATOR = register("grocery_deliverator", new GroceryDeliverator(GroceryDeliveratorTile::new, Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion()), false);
	
	/* Crafting */
	public static RegistryObject<Block> SPRAY_ON_FULL 		= register("biopolymer_adhesive_block", new BreakableBlock(Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion().strength(50.0F, 1200.0F)),  false);

	/* Other */
	public static RegistryObject<Block> SPRAY_ON_SIDE		= register("biopolymer_adhesive", new SprayOnBlock(Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion().strength(50.0F, 1200.0F)),  false);
	
	/* Register Methods */
	private static RegistryObject<Block> register(String name, Block block, Item.Properties properties, boolean hasItem, boolean is3D) {
		if (hasItem) {
			Item item = (is3D)? new BlockItem3D(block, properties) : new BlockItem(block, properties);
			RegistryHandler.ITEMS.register(name, () -> item);
		}
		return RegistryHandler.BLOCKS.register(name, () -> block);
	}	
	
	private static RegistryObject<Block> register(String name, Block block, Item.Properties properties, boolean hasItem) {
		return register(name, block, properties, hasItem, false);
	}	
	
	private static RegistryObject<Block> register(String name, Block block, boolean hasItem, boolean is3D) {
		return register(name, block, new Item.Properties(), hasItem, is3D);
	}
	
	private static RegistryObject<Block> register(String name, Block block, boolean hasItem) {
		return register(name, block, new Item.Properties(), hasItem, false);
	}	
	
	public static void init() {};	
	
}
