package com.bug1312.cloudyporkchops.util.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class InventoryFoodHelper {

	public static ItemStack getRandomFood(PlayerInventory inv) {
		List<ItemStack> items = new ArrayList<>();
		List<ItemStack> result = new ArrayList<>();
		
		items.addAll(inv.items);
		items.addAll(inv.offhand);
		
		for(ItemStack stack : items) if(stack.getItem().isEdible()) result.add(stack);
		
		if(result.size() == 0) return null;
		
		return result.get((int) (Math.random() * result.size()));
	}
}
