package com.bug1312.cloudyporkchops.common.init;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.common.tile.invention.GroceryDeliveratorTile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class CloudyTiles {
	
	public static RegistryObject<TileEntityType<GroceryDeliveratorTile>> GROCERY_DELIVERATOR = register("grocery_deliverator", () -> TileEntityType.Builder.of(GroceryDeliveratorTile::new, CloudyBlocks.GROCERY_DELIVERATOR.get()).build(null));

	/* Register Method */
	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String id, Supplier<TileEntityType<T>> supplier) {
		RegistryObject<TileEntityType<T>> registryItem = RegistryHandler.TILE_ENTITY_TYPES.register(id, supplier);
		return registryItem;
	}
	
	public static void init() {};	

}
