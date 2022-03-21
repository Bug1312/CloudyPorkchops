package com.bug1312.cloudyporkchops.common.items.inventions;

import com.bug1312.cloudyporkchops.common.init.CloudyItems;
import com.bug1312.cloudyporkchops.common.items.BlockItem;
import com.bug1312.cloudyporkchops.common.items.IItem3D;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;
import com.bug1312.cloudyporkchops.util.consts.CloudyTranslations;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.RegistryObject;

public class GroceryDeliveratorItem extends BlockItem implements IItem3D {
	
	public GroceryDeliveratorItem(RegistryObject<Block> block, Properties properties) {
		super(block, properties);
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return hasLocation(stack.getOrCreateTag());
	}
	
	public String getDescriptionId(ItemStack stack) {
		return hasLocation(stack.getOrCreateTag()) ? CloudyTranslations.GROCERY_DELIVERATOR_LOCATION.getString() : super.getDescriptionId(stack);
	}
	
	public static boolean hasLocation(CompoundNBT nbt) {
		return (nbt.contains(CloudyNBTKeys.EXIT_PORTAL_POS) && nbt.contains(CloudyNBTKeys.EXIT_PORTAL_DIM));
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		if(player != null && player.isShiftKeyDown()) {
			if(!context.getLevel().isClientSide()) {
				CompoundNBT nbt = context.getItemInHand().getOrCreateTag();
				BlockPos pos = context.getClickedPos().relative(context.getClickedFace()).above((context.getClickedFace() == Direction.DOWN) ? 0 : 1);
				String dim = context.getLevel().dimension().location().toString();
				
				if(hasLocation(nbt)) addBlankItem(context.getItemInHand(), player);
				else addLocationItem(context.getItemInHand(), player, pos, dim);
			}
			
			return ActionResultType.SUCCESS;
		}
		return super.useOn(context);
	}

	private void addLocationItem(ItemStack itemstack, PlayerEntity player, BlockPos pos, String dim) {
		ItemStack newStack = new ItemStack(CloudyItems.GROCERY_DELIVERATOR.get(), 1);
        CompoundNBT nbt = itemstack.hasTag() ? itemstack.getTag().copy() : new CompoundNBT();
        
        nbt.put(CloudyNBTKeys.EXIT_PORTAL_POS, NBTUtil.writeBlockPos(pos));
		nbt.putString(CloudyNBTKeys.EXIT_PORTAL_DIM, dim);
        
		newStack.setTag(nbt);
        
		if (!player.isCreative() || (player.isCreative() && itemstack.getCount() == 1)) itemstack.shrink(1);
        if (!player.inventory.add(newStack)) player.drop(newStack, false);
	}
	
	private void addBlankItem(ItemStack itemstack, PlayerEntity player) {
		ItemStack newStack = new ItemStack(CloudyItems.GROCERY_DELIVERATOR.get(), 1);
        CompoundNBT nbt = itemstack.hasTag() ? itemstack.getTag().copy() : new CompoundNBT();
        
        nbt.remove(CloudyNBTKeys.EXIT_PORTAL_POS);
		nbt.remove(CloudyNBTKeys.EXIT_PORTAL_DIM);
        
		newStack.setTag(nbt);
        
		if (!player.isCreative() || (player.isCreative() && itemstack.getCount() == 1)) itemstack.shrink(1);
        if (!player.inventory.add(newStack)) player.drop(newStack, false);
	}
	
}
