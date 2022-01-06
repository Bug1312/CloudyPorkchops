package com.bug1312.common.init;

import java.util.function.Supplier;

import com.bug1312.common.RegistryHandler;
import com.bug1312.common.items.Item3D;

import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.fml.RegistryObject;

public class Items {

	public static RegistryObject<Item> SPRAY_ON_SHOES = register(
			"spray_on_shoes",
			() -> new Item3D(new Properties())
		);

	private static RegistryObject<Item> register(String id, final Supplier<? extends Item> supplier) {
		return RegistryHandler.ITEMS.register(id, supplier);
	}
}
