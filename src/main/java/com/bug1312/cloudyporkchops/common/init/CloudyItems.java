package com.bug1312.cloudyporkchops.common.init;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.common.RegistryHandler;
import com.bug1312.cloudyporkchops.common.items.CloudyArmorMaterials;
import com.bug1312.cloudyporkchops.common.items.inventions.ShoesCan;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;

public class CloudyItems {

	public static RegistryObject<Item> SPRAY_ON_SHOES_CAN = register(
			"spray_on",
			() -> new ShoesCan(new Properties().durability(20))
		);
	
	public static RegistryObject<Item> SPRAY_ON_BOOTS = register(
			"spray_on_boots",
			() -> new ArmorItem(CloudyArmorMaterials.Armor.SPRAY_ON, EquipmentSlotType.FEET, new Properties().setNoRepair())
		);

	private static RegistryObject<Item> register(String id, final Supplier<? extends Item> supplier) {
		return RegistryHandler.ITEMS.register(id, supplier);
	}
}
