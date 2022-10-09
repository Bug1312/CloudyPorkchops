package com.bug1312.cloudyporkchops.common.item.invention;

import com.bug1312.cloudyporkchops.common.init.CloudyItems;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;
import com.bug1312.cloudyporkchops.util.consts.CloudyTranslations;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class GroceryDeliveratorItem extends BlockItem {

	public GroceryDeliveratorItem(RegistryObject<Block> block, Properties properties) {
		super(block.get(), properties);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return hasLocation(stack.getOrCreateTag());
	}

	public String getDescriptionId(ItemStack stack) {
		return hasLocation(stack.getOrCreateTag()) ? CloudyTranslations.GROCERY_DELIVERATOR_LOCATION.toString() : super.getDescriptionId(stack);
	}

	public static boolean hasLocation(CompoundTag nbt) {
		return (nbt.contains(CloudyNBTKeys.EXIT_PORTAL_POS) && nbt.contains(CloudyNBTKeys.EXIT_PORTAL_DIM));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		if (player != null && player.isShiftKeyDown()) {
			if (!context.getLevel().isClientSide()) {
				CompoundTag nbt = context.getItemInHand().getOrCreateTag();
				BlockPos pos = context.getClickedPos().relative(context.getClickedFace()).above((context.getClickedFace() == Direction.DOWN) ? 0 : 1);
				String dim = context.getLevel().dimension().location().toString();

				if (hasLocation(nbt)) addBlankItem(context.getItemInHand(), player);
				else addLocationItem(context.getItemInHand(), player, pos, dim);
			}

			return InteractionResult.SUCCESS;
		}
		return super.useOn(context);
	}

	private void addLocationItem(ItemStack itemstack, Player player, BlockPos pos, String dim) {
		ItemStack newStack = new ItemStack(CloudyItems.GROCERY_DELIVERATOR.get(), 1);
		CompoundTag nbt = itemstack.getOrCreateTag().copy();

        nbt.put(CloudyNBTKeys.EXIT_PORTAL_POS, NbtUtils.writeBlockPos(pos));
		nbt.putString(CloudyNBTKeys.EXIT_PORTAL_DIM, dim);

		newStack.setTag(nbt);

		if (!player.isCreative() || (player.isCreative() && itemstack.getCount() == 1)) itemstack.shrink(1);
        if (!player.getInventory().add(newStack)) player.drop(newStack, false);
	}

	private void addBlankItem(ItemStack itemstack, Player player) {
		ItemStack newStack = new ItemStack(CloudyItems.GROCERY_DELIVERATOR.get(), 1);
		CompoundTag nbt = itemstack.getOrCreateTag().copy();

        nbt.remove(CloudyNBTKeys.EXIT_PORTAL_POS);
		nbt.remove(CloudyNBTKeys.EXIT_PORTAL_DIM);

		newStack.setTag(nbt);

		if (!player.isCreative() || (player.isCreative() && itemstack.getCount() == 1)) itemstack.shrink(1);
        if (!player.getInventory().add(newStack)) player.drop(newStack, false);
	}

}
