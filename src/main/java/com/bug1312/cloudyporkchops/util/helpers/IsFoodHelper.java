package com.bug1312.cloudyporkchops.util.helpers;

import com.bug1312.cloudyporkchops.common.init.CloudyTags;
import com.bug1312.cloudyporkchops.common.init.CloudyTags.EntityTypes;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;

public class IsFoodHelper {
	public static boolean isFood(Entity entity) {
		if (entity instanceof ItemEntity) {
			ItemStack stack = ((ItemEntity) entity).getItem();
			Item item = stack.getItem();
			if (item.getFoodProperties(stack, null) != null
				|| item.isEdible()
				|| item instanceof PotionItem
				|| stack.is(CloudyTags.Items.EXTRA_FOODS)
			) return true;
		}
		if (entity.getType().is(EntityTypes.FOODIMAL)) return true;

		return false;
	}
}
