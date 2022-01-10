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
		if(world.isClientSide()) {
			
			// Self Check
			if(entity.getRotationVector().x >= 80) {
				if(tickAfterChange == 0) tickAfterChange = (int) world.getGameTime();
				currentUse = SprayCanUse.SELF;
			}
			
			
			if (tickAfterChange != 0 && world.getGameTime() >= tickAfterChange + requiredTicks) {
				switch(currentUse) {
				case NONE: default: break;
				case BLOCK: break;
				case ENTITY: break;
				case SELF:
					ItemStack boots = new ItemStack(Items.SPRAY_ON_BOOTS.get());

					boots.enchant(Enchantments.BINDING_CURSE, 1);
					entity.setItemSlot(EquipmentSlotType.FEET, boots);
					resetUse();
					break;
				}
			} else {
				switch(currentUse) {
				case NONE: default: break;
				case BLOCK: break;
				case ENTITY: break;
				case SELF: 
					if(entity.getRotationVector().x < 80) 
						resetUse(); 
					break;
				}
			}
		}

		// Destroy
		if (world.getGameTime() % 20 == 0) 
			itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(e.getUsedItemHand()));
		
		super.onUseTick(world, entity, itemstack, ticksUsed);
	}
	
	private void resetUse() {
		currentUse = SprayCanUse.NONE;
		tickAfterChange = 0;
	}

	
	@Override
	public void releaseUsing(ItemStack itemstack, World world, LivingEntity entity, int totalTicksUsed) {
		tickAfterChange = 0;
		super.releaseUsing(itemstack, world, entity, totalTicksUsed);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
		return super.onLeftClickEntity(stack, player, entity);
	}

	public enum SprayCanUse {
		NONE, SELF, ENTITY, BLOCK;
	}
}
