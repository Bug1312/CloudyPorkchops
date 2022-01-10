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

	private int requiredTicks = 2 * 20;
	private SprayCanUse currentUse = SprayCanUse.NONE;
	private int tickAfterChange;

	public ShoesCan(Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
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
	public void onUseTick(World world, LivingEntity entity, ItemStack itemstack, int ticksUsed) {
		if(entity.getYHeadRot() > 60) {
			if(tickAfterChange != 0) tickAfterChange = (int) world.dayTime();
			currentUse = SprayCanUse.SELF;
		} else if(currentUse == SprayCanUse.SELF) currentUse = SprayCanUse.NONE;
		
		if (tickAfterChange != 0 && world.dayTime() >= tickAfterChange + requiredTicks) {
			switch(currentUse) {
			case NONE: default: break;
			case BLOCK:
				
				break;
			case ENTITY:

				break;
			case SELF:
				ItemStack boots = new ItemStack(Items.SPRAY_ON_BOOTS.get());

				boots.enchant(Enchantments.BINDING_CURSE, 1);
				entity.setItemSlot(EquipmentSlotType.FEET, boots);
				break;
			}
		}
		super.onUseTick(world, entity, itemstack, ticksUsed);
	}
	
	

	@Override
	public void releaseUsing(ItemStack itemstack, World world, LivingEntity entity, int totalTicksUsed) {
		tickAfterChange = 0;
		super.releaseUsing(itemstack, world, entity, totalTicksUsed);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		// TODO Auto-generated method stub
		return super.onLeftClickEntity(stack, player, entity);
	}

	public enum SprayCanUse {
		NONE, SELF, ENTITY, BLOCK;
	}
}
