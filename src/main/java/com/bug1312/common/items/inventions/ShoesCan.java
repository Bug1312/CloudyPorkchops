package com.bug1312.common.items.inventions;

import java.util.UUID;

import com.bug1312.common.init.Items;
import com.bug1312.common.items.Item3D;
import com.bug1312.util.RaytraceHelper;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

// WIP
/*
 * Plans:
 * 	Make item lock other entities
 * 	Possibly create block face (like Create super glue)
 * 	✅ When looking down, give player spray on shoes
 * 	✅ Damage when sprayed; even if in air
 */
public class ShoesCan extends Item3D {

	private int requiredTicks = 2 * 20;
	private SprayCanUse currentUse = SprayCanUse.NONE;
	private int tickAfterChange;
	private UUID currentEntityUUID;
	private int entityBackupTime;
	private BlockPos currentBlockPos;
	private Direction currentBlockFace;

	public ShoesCan(Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 72000;
	}

	public UseAction getUseAnimation(ItemStack itemstack) {
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
		// Block Check
		BlockRayTraceResult blockLookingAt = RaytraceHelper.getPlayerBlockResult(entity, entity.getAttributeValue(ForgeMod.REACH_DISTANCE.get()));
		if (blockLookingAt != null && entity.getRotationVector().x < 80) {
			BlockPos lookingBlockPos = blockLookingAt.getBlockPos();
			
			currentBlockFace = blockLookingAt.getDirection();

			if(currentBlockPos == null) {
				tickAfterChange = (int) world.getGameTime();
				currentBlockPos = lookingBlockPos;
				currentUse = SprayCanUse.BLOCK;
			} else if (currentBlockPos.getX() != lookingBlockPos.getX()
					|| currentBlockPos.getY() != lookingBlockPos.getY()
					|| currentBlockPos.getZ() != lookingBlockPos.getZ()) {
				resetUse();
			}
			
		}
		
		if(!world.isClientSide()) {
			// Entity Check
			EntityRayTraceResult entityLookingAt = RaytraceHelper.getHitResult(entity, entity.getAttributeValue(ForgeMod.REACH_DISTANCE.get()));
			if (currentEntityUUID == null && entityLookingAt != null) {
				tickAfterChange = (int) world.getGameTime();
				currentEntityUUID = entityLookingAt.getEntity().getUUID();
				currentUse = SprayCanUse.ENTITY;
			}
			if(entityLookingAt != null) 
				entityBackupTime = 15;
			
			// Self Check
			if(entity.getRotationVector().x >= 80) {
				if(tickAfterChange == 0) tickAfterChange = (int) world.getGameTime();
				currentUse = SprayCanUse.SELF;
			}
			
			// If completed
			if (tickAfterChange != 0 && world.getGameTime() >= tickAfterChange + requiredTicks) {
				switch(currentUse) {
					case NONE: default: break;
					case BLOCK: sprayOnBlock(world, currentBlockPos, currentBlockFace); break;
					case ENTITY:  sprayOnEntity(entityLookingAt.getEntity()); break;
					case SELF: sprayOnEntity(entity); break;
				}
				if(currentUse != SprayCanUse.NONE) resetUse();
			} else {
				switch(currentUse) {
					default: break;
					case ENTITY:
						entityBackupTime--;
						
						if (entityBackupTime <= 0
								&& (entityLookingAt == null || entityLookingAt.getEntity().getUUID() != currentEntityUUID))
							sprayOnEntity(entityLookingAt.getEntity());
						break;
					case SELF:  if(entity.getRotationVector().x < 80) resetUse(); break;
				}
				
			}
		}

		// Destroy
		if (world.getGameTime() % 20 == 0) 
			itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(e.getUsedItemHand()));
		
		super.onUseTick(world, entity, itemstack, ticksUsed);
	}
	
	@Override
	public void releaseUsing(ItemStack itemstack, World world, LivingEntity entity, int totalTicksUsed) {
		resetUse();
		super.releaseUsing(itemstack, world, entity, totalTicksUsed);
	}
	
	private void resetUse() {
		currentUse = SprayCanUse.NONE;
		tickAfterChange = 0;
		currentEntityUUID = null;
		currentBlockPos = null;
		currentBlockFace = null;
	}

	private void sprayOnEntity(Entity entity) {
		if(entity instanceof PlayerEntity) {
			sprayOnPlayer((PlayerEntity) entity);
			return;
		}	
		System.out.println(entity.getName().getString());
	}
	
	private void sprayOnBlock(World world, BlockPos pos, Direction face) {
		BlockState state = world.getBlockState(pos);
		System.out.println(state.getBlock().getName().getString() + ", " + face);
	}
	
	private void sprayOnPlayer(PlayerEntity player) {
		ItemStack boots = new ItemStack(Items.SPRAY_ON_BOOTS.get());

		boots.enchant(Enchantments.BINDING_CURSE, 1);
		player.setItemSlot(EquipmentSlotType.FEET, boots);
	}
	
	public enum SprayCanUse {
		NONE, SELF, ENTITY, BLOCK;
	}
}
