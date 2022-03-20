package com.bug1312.cloudyporkchops.common.items.inventions;

import com.bug1312.cloudyporkchops.common.items.BlockItem;
import com.bug1312.cloudyporkchops.common.items.IItem3D;
import com.bug1312.cloudyporkchops.util.PlayerSpawnHelper.Location;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraftforge.fml.RegistryObject;

public class GroceryDeliveratorItem extends BlockItem implements IItem3D {

	public Location location;
	
	public GroceryDeliveratorItem(RegistryObject<Block> block, Properties properties) {
		super(block, properties);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		if(player != null && player.isShiftKeyDown()) {
			if(!context.getLevel().isClientSide()) {
				int amt = (context.getClickedFace() == Direction.DOWN)? 0 : 1;
				Location newLocation = new Location(context.getClickedPos().relative(context.getClickedFace()).above(amt), context.getLevel().dimension().location().toString());
				if(location != null && newLocation.equals(location)) {
					location = null;
				} else {
					location = newLocation;
				}
			}
			
			return ActionResultType.SUCCESS;
		}
		return super.useOn(context);
	}

	
}
