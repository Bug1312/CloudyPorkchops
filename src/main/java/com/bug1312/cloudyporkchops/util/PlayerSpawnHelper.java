package com.bug1312.cloudyporkchops.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class PlayerSpawnHelper {
	public static Location getSpawnLocation(UUID entityUUID, World world) {
		ServerPlayerEntity player = world.getServer().getPlayerList().getPlayer(entityUUID);

		RegistryKey<World> dim = player.getRespawnDimension();
		ServerWorld serverDim = world.getServer().getLevel(dim);
		BlockPos pos = player.getRespawnPosition();
		if (pos == null) pos = serverDim.getSharedSpawnPos();

		return new Location(pos, serverDim);
	}
	
	public static class Location {
		public BlockPos pos;
		public ServerWorld dim;
		public UUID backupUUID;
		
		public Location() {}
		public Location(BlockPos _pos, ServerWorld _dim) { pos = _pos; dim = _dim; }
		public Location(Map<BlockPos, ServerWorld> map) {
			pos = map.entrySet().iterator().next().getKey();
			dim = map.get(pos);
		}
		public Location(UUID uuid) { backupUUID = uuid; }
		
		public Map<BlockPos, ServerWorld> toHash() {
			Map<BlockPos, ServerWorld> map = new HashMap<>();
			map.put(pos, dim);
			return map;
		}
		
		public boolean equals(Location otherLocation) {
			return (pos == otherLocation.pos && dim == otherLocation.dim);
		}
	}
}
