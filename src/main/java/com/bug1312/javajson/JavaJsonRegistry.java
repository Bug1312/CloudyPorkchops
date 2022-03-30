package com.bug1312.javajson;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

public class JavaJsonRegistry {
	
	public static <T extends TileEntity> void bindTileEntityRenderer(TileEntityType<T> tileEntityType, ResourceLocation location) {
		ModelReloadListener.bindTileEntityRenderer(tileEntityType, location);
		ModelReloadListener.tileEntitiesForReload.put(tileEntityType, location);
	}
	
}
