package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.client.render.Item3DRendering;
import com.bug1312.cloudyporkchops.common.item.IItem3D;
import com.bug1312.cloudyporkchops.common.item.invention.ForkKnifeSpooninator;
import com.bug1312.cloudyporkchops.common.item.invention.GroceryDeliveratorItem;
import com.bug1312.cloudyporkchops.common.item.invention.ShoesCan;
import com.bug1312.cloudyporkchops.common.material.CloudyArmorMaterials;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;

public class CloudyItems {

	/* Tools */
	public static RegistryObject<Item> SPRAY_ON_SHOES_CAN 		= register("spray_on_shoes", 		 new ShoesCan(new Properties().durability(20)));
	public static RegistryObject<Item> SUPER_SPRAY_ON_SHOES_CAN = register("super_spray_on_shoes",	 new ShoesCan(new Properties().durability(50)));
	public static RegistryObject<Item> FORK_KNIFE_SPOON_INATOR  = register("fork_knife_spooninator", new ForkKnifeSpooninator(new Properties().durability(450)));

	/* Crafting */
	public static RegistryObject<Item> SPRAY_ON_SHARDS 			= register("biopolymer_adhesive_shards", new Item(new Properties()));

	/* Foods */
	public static RegistryObject<Item> JELLO 					= register("jello", new Item(new Properties().food(CloudyFoodBuilders.JELLO)));
	
	/* Armor */
	public static RegistryObject<Item> SPRAY_ON_BOOTS 			= register("spray_on_boots", new ArmorItem(CloudyArmorMaterials.Armor.SPRAY_ON, EquipmentSlotType.FEET, new Properties().setNoRepair()));
	
	/* Block Items */
	public static RegistryObject<Item> GROCERY_DELIVERATOR		= register("grocery_deliverator", new GroceryDeliveratorItem(CloudyBlocks.GROCERY_DELIVERATOR, new Item.Properties()));
	
	/* Register Method */
	private static RegistryObject<Item> register(String id,  Item item) {
		RegistryObject<Item> registryItem = RegistryHandler.ITEMS.register(id, () -> item);
		if(item instanceof IItem3D) Item3DRendering.ITEMS_3D.add(registryItem);
		return registryItem;
	}
	
	public static void init() {};	
	
}
