package com.bug1312.cloudyporkchops.common.item.invention;

import java.util.UUID;

import com.bug1312.cloudyporkchops.common.init.CloudyBlocks;
import com.bug1312.cloudyporkchops.common.init.CloudyItems;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;
import com.bug1312.cloudyporkchops.util.helpers.DirectionHelper;
import com.bug1312.cloudyporkchops.util.helpers.RaytraceHelper;
import com.bug1312.cloudyporkchops.util.helpers.SecondsToTickHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeMod;

public class ShoesCan extends Item {

	private int requiredTicks = SecondsToTickHelper.toTicks(2);
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

	@Override
	public UseAnim getUseAnimation(ItemStack itemstack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		player.startUsingItem(hand);
		return InteractionResultHolder.consume(itemstack);
	}

	@Override
	public void onUseTick(Level world, LivingEntity entity, ItemStack itemstack, int ticksUsed) {
		// Block Check
		BlockHitResult blockLookingAt = RaytraceHelper.getPlayerBlockResult(entity, entity.getAttributeValue(ForgeMod.REACH_DISTANCE.get()));
		if (blockLookingAt != null && entity.getRotationVector().x < 80) {
			BlockPos lookingBlockPos = blockLookingAt.getBlockPos();

			currentBlockFace = blockLookingAt.getDirection();

			if (currentBlockPos == null) {
				tickAfterChange = (int) world.getGameTime();
				currentBlockPos = lookingBlockPos;
				currentUse = SprayCanUse.BLOCK;
			} else if (!currentBlockPos.equals(lookingBlockPos)) resetUse();


		}

		if (!world.isClientSide()) {
			// Entity Check
			EntityHitResult entityLookingAt = RaytraceHelper.getHitResult(entity, entity.getAttributeValue(ForgeMod.REACH_DISTANCE.get()));
			if (currentEntityUUID == null && entityLookingAt != null) {
				tickAfterChange = (int) world.getGameTime();
				currentEntityUUID = entityLookingAt.getEntity().getUUID();
				currentUse = SprayCanUse.ENTITY;
			}
			if (entityLookingAt != null) entityBackupTime = 15;

			// Self Check
			if (entity.getRotationVector().x >= 80) {
				if (tickAfterChange == 0) tickAfterChange = (int) world.getGameTime();
				currentUse = SprayCanUse.SELF;
			}

			// If completed
			if (tickAfterChange != 0 && world.getGameTime() >= tickAfterChange + requiredTicks) {
				switch (currentUse) {
					case NONE: default: break;
					case BLOCK: sprayOnBlock(world, currentBlockPos, currentBlockFace); break;
					case ENTITY:
						if (entityLookingAt.getEntity() instanceof Player) sprayOnPlayer((Player) entity);
						else sprayOnEntity(entityLookingAt.getEntity()); break;
					case SELF: sprayOnPlayer((Player) entity); break;
				}
				if (currentUse != SprayCanUse.NONE) resetUse();
			} else {
				switch (currentUse) {
					default: break;
					case BLOCK: if (world.isEmptyBlock(currentBlockPos)) resetUse(); break;
					case ENTITY:
						entityBackupTime--;
						if ((entityLookingAt != null && entityLookingAt.getEntity().getUUID() != currentEntityUUID)
								|| (entityBackupTime <= 0 && entityLookingAt == null)
						) resetUse(); break;
					case SELF: if (entity.getRotationVector().x < 80) resetUse(); break;
				}

			}
		}

		// Lose durability
		if (world.getGameTime() % 20 == 0) itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(e.getUsedItemHand()));


		super.onUseTick(world, entity, itemstack, ticksUsed);
	}

	@Override
	public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entity, int totalTicksUsed) {
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
		if (entity instanceof Mob) {
			CompoundTag nbt = entity.serializeNBT();
			nbt.putShort(CloudyNBTKeys.SPRAYED_ON, (short) (SecondsToTickHelper.toTicks(10)));
			entity.deserializeNBT(nbt);
		}
	}

	private void sprayOnBlock(Level world, BlockPos pos, Direction face) {
		BlockPos newBlockPos = pos.relative(face);
		BlockState state = world.getBlockState(newBlockPos);
		Direction newBlockDir = face.getOpposite();
		BooleanProperty newBlockDirProp = DirectionHelper.toProperty(newBlockDir);

		Boolean flag = HorizontalDirectionalBlock.canSupportCenter(world, newBlockPos.relative(newBlockDir), newBlockDir.getOpposite());

		if (flag) {
			if (state.getBlock() == CloudyBlocks.SPRAY_ON_SIDE.get()) world.setBlock(newBlockPos, state.setValue(newBlockDirProp, true), 2);
			if (state.getBlock() == Blocks.AIR) world.setBlock(newBlockPos, CloudyBlocks.SPRAY_ON_SIDE.get().defaultBlockState().setValue(newBlockDirProp, true), 2);
		}

	}

	private void sprayOnPlayer(Player player) {
		ItemStack boots = new ItemStack(CloudyItems.SPRAY_ON_BOOTS.get());

		boots.enchant(Enchantments.BINDING_CURSE, 1);
		player.setItemSlot(EquipmentSlot.FEET, boots);
	}

	public enum SprayCanUse { NONE, SELF, ENTITY, BLOCK; }
}
