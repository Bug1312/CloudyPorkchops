package com.bug1312.cloudyporkchops.common.item.invention;

import com.bug1312.cloudyporkchops.common.item.Item3D;
import com.bug1312.cloudyporkchops.util.helpers.InventoryFoodHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

public class ForkKnifeSpooninator extends Item3D {

	public ForkKnifeSpooninator(Properties properties) {
		super(properties);
	}

	@Override
	public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
		if(armorType == EquipmentSlotType.HEAD) return true;
		return super.canEquip(stack, armorType, entity);
	}
	
	@Override
	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
		return EquipmentSlotType.HEAD;
	}
	
	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		FoodStats foodStats = player.getFoodData();
		ItemStack food = InventoryFoodHelper.getRandomFood(player.inventory);
		
		if(foodStats.needsFood() && food != null) {
			player.eat(world, food);
			stack.hurtAndBreak(1, null, null);
		}
		
		super.onArmorTick(stack, world, player);
	}
	
}
