package com.bug1312.cloudyporkchops.common.init;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.common.item.invention.GroceryDeliveratorItem;
import com.bug1312.cloudyporkchops.common.item.invention.ShoesCan;
import com.bug1312.cloudyporkchops.common.material.CloudyArmorMaterials;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.RegistryObject;

public class CloudyItems {

	/* Tools */
	public static RegistryObject<Item>
	SPRAY_ON_SHOES_CAN 		 = register("spray_on_shoes", 		 () -> new ShoesCan(new Properties().durability(20))),
	SUPER_SPRAY_ON_SHOES_CAN = register("super_spray_on_shoes",	 () -> new ShoesCan(new Properties().durability(50)));

	/* Crafting */
	public static RegistryObject<Item>
	SPRAY_ON_SHARDS = register("biopolymer_adhesive_shards", () -> new Item(new Properties()));

	/* Foods */
	public static RegistryObject<Item>
	JELLO = register("jello", () -> new Item(new Properties().food(CloudyFoodBuilders.JELLO)));

	/* Armor */
	public static RegistryObject<Item>
	SPRAY_ON_BOOTS = register("spray_on_boots", () -> new ArmorItem(CloudyArmorMaterials.SPRAY_ON, EquipmentSlot.FEET, new Properties().setNoRepair()));

	/* Block Items */
	public static RegistryObject<Item>
	GROCERY_DELIVERATOR = register("grocery_deliverator", () -> new GroceryDeliveratorItem(CloudyBlocks.GROCERY_DELIVERATOR, new Item.Properties()));
	
	/* Register Method */
	private static RegistryObject<Item> register(String id, Supplier<Item> item) {
		RegistryObject<Item> registryItem = RegistryHandler.ITEMS.register(id, item);
		return registryItem;
	}

	public static void init() {};

}
