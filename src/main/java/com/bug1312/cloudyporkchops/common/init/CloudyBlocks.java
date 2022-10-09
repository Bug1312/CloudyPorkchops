package com.bug1312.cloudyporkchops.common.init;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.common.block.food.Jello;
import com.bug1312.cloudyporkchops.common.block.invention.GroceryDeliverator;
import com.bug1312.cloudyporkchops.common.block.invention.InvisibleTable;
import com.bug1312.cloudyporkchops.common.block.invention.SprayOnBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

public class CloudyBlocks {

	/* Decoration */
	public static RegistryObject<Block> INVISIBLE_TABLE 	= register("invisible_table", () -> new InvisibleTable(Properties.of(Material.WOOD).noOcclusion()), true);

	/* Foods */
	public static RegistryObject<Block> JELLO 				= register("jello_block", () -> new Jello(Properties.of(Material.CLAY, MaterialColor.COLOR_ORANGE).speedFactor(0.4F).noOcclusion().sound(SoundType.HONEY_BLOCK)), true);

	/* Useful */
	public static RegistryObject<Block> GROCERY_DELIVERATOR = register("grocery_deliverator", () -> new GroceryDeliverator(Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion()), false);

	/* Crafting */
	public static RegistryObject<Block> SPRAY_ON_FULL 		= register("biopolymer_adhesive_block", () -> new HalfTransparentBlock(Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion().strength(50.0F, 1200.0F)),  false);

	/* Other */
	public static RegistryObject<Block> SPRAY_ON_SIDE		= register("biopolymer_adhesive", () -> new SprayOnBlock(Properties.of(Material.CLAY, MaterialColor.COLOR_PURPLE).noOcclusion().strength(50.0F, 1200.0F)),  false);

	/* Register Methods */
	private static RegistryObject<Block> register(String name, Supplier<Block> block, Item.Properties properties, boolean hasItem) {
		RegistryObject<Block> obj = RegistryHandler.BLOCKS.register(name, block);
		if (hasItem) {
			Supplier<Item> item = () -> new BlockItem(obj.get(), properties);
			RegistryHandler.ITEMS.register(name, item);
		}
		return obj;
	}


	private static RegistryObject<Block> register(String name, Supplier<Block> block, boolean hasItem) {
		return register(name, block, new Item.Properties(), hasItem);
	}

	public static void init() {};

}
