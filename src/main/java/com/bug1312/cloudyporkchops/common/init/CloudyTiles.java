package com.bug1312.cloudyporkchops.common.init;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.common.tile.invention.GroceryDeliveratorTile;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

public class CloudyTiles {

	public static RegistryObject<BlockEntityType<GroceryDeliveratorTile>> GROCERY_DELIVERATOR = register("grocery_deliverator", () -> BlockEntityType.Builder.of(GroceryDeliveratorTile::new, CloudyBlocks.GROCERY_DELIVERATOR.get()).build(null));

	/* Register Method */
	private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, Supplier<BlockEntityType<T>> supplier) {
		RegistryObject<BlockEntityType<T>> registryItem = RegistryHandler.TILE_ENTITY_TYPES.register(id, supplier);
		return registryItem;
	}

	public static void init() {};

}
