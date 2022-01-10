package com.bug1312.common.items.inventions;

import com.bug1312.common.init.Items;
import com.bug1312.common.items.Item3D;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

// WIP
/*
 * Plans:
 * 	Make item lock other entities
 * 	Possibly create block face (like Create super glue)
 * 	When looking down, give player spray on shoes
 * 	Damage when sprayed; even if in air
 */
public class ShoesCan extends Item3D {

	public ShoesCan(Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 2 * 20;
	}

	public UseAction getUseAnimation(ItemStack p_77661_1_) {
		return UseAction.CROSSBOW;
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return ActionResult.consume(itemstack);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entity) {
		if(!entity.level.isClientSide) {
			ItemStack boots = new ItemStack(Items.SPRAY_ON_BOOTS.get());
			
			boots.enchant(Enchantments.BINDING_CURSE, 1);
			entity.setItemSlot(EquipmentSlotType.FEET, boots);
		}
		
		return super.finishUsingItem(itemstack, world, entity);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
		// TODO Auto-generated method stub
		super.onUsingTick(stack, player, count);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		// TODO Auto-generated method stub
		return super.onLeftClickEntity(stack, player, entity);
	}
}
