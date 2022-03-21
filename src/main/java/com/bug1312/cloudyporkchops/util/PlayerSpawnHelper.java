package com.bug1312.cloudyporkchops.util;

import java.util.UUID;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PlayerSpawnHelper {
	public static Location getSpawnLocation(UUID entityUUID, World world) {
		ServerPlayerEntity player = world.getServer().getPlayerList().getPlayer(entityUUID);

		RegistryKey<World> dim = player.getRespawnDimension();
		ServerWorld serverDim = world.getServer().getLevel(dim);
		BlockPos pos = player.getRespawnPosition();
		if (pos == null) pos = serverDim.getSharedSpawnPos();
		
		return new Location(pos.above(), serverDim.dimension().location().toString());
	}
	
	public static class Location {
		public BlockPos pos;
		public String dim;
		public UUID backupUUID;
		
		public Location() {}
		public Location(BlockPos _pos, String _dim) { pos = _pos; dim = _dim; }
		public Location(UUID uuid) { backupUUID = uuid; }
		
		public boolean equals(Location otherLocation) {
			boolean exactExists = (pos != null && dim != null && otherLocation.pos != null && otherLocation.dim != null);
			boolean uuidExists  = (backupUUID != null && otherLocation.backupUUID != null);
			
			boolean exactMatch  = exactExists && (pos.equals(otherLocation.pos) && dim.equals(otherLocation.dim));
			boolean uuidMatch   = uuidExists && backupUUID.equals(otherLocation.backupUUID);
			
			return exactMatch || uuidMatch;
		}
		
		public ServerWorld getDimension(World level) {
			return level.getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dim)));
		}
	}
}
