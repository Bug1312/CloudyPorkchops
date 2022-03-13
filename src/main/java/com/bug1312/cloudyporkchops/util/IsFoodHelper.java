package com.bug1312.cloudyporkchops.util;

import com.bug1312.cloudyporkchops.common.init.CloudyTags;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.PotionItem;

public class IsFoodHelper {
	public static boolean isFood(Entity entity) {
		if (entity instanceof ItemEntity) {
			Item item = ((ItemEntity) entity).getItem().getItem();
			if (item.getFoodProperties() != null 
				|| item instanceof PotionItem
				|| CloudyTags.Items.EXTRA_FOODS.contains(item)
			) return true;
		}
		if (CloudyTags.EntityTypes.FOODIMAL.contains(entity.getType())) return true;
		
		return false;
	}
}
