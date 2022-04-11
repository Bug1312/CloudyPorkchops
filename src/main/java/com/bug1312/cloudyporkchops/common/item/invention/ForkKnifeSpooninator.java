package com.bug1312.cloudyporkchops.common.item.invention;

import com.bug1312.cloudyporkchops.client.render.item.model.ForkKnifeSpooninatorModel;
import com.bug1312.cloudyporkchops.common.item.IItem3D;
import com.bug1312.cloudyporkchops.common.material.CloudyArmorMaterials;
import com.bug1312.cloudyporkchops.util.helpers.InventoryFoodHelper;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ForkKnifeSpooninator extends ArmorItem implements IItem3D {

	public ForkKnifeSpooninator(Properties properties) {
		super(CloudyArmorMaterials.Armor.SPRAY_ON, EquipmentSlotType.HEAD, properties);
	}

//	@Override
//	public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
//		if(armorType == EquipmentSlotType.HEAD) return true;
//		return super.canEquip(stack, armorType, entity);
//	}
//	
//	@Override
//	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
//		return EquipmentSlotType.HEAD;
//	}
	
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
	
	@SuppressWarnings("unchecked")
	@OnlyIn(Dist.CLIENT)
	@Override
	public <M extends BipedModel<?>> M getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, M _default) {
		return (M) new ForkKnifeSpooninatorModel();
	}

	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return new ForkKnifeSpooninatorModel().getTexture().toString();
	}
	
}
