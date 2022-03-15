package com.bug1312.cloudyporkchops.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SpawnLocationHelper {
	public static Map<BlockPos, ServerWorld> getSpawnLocation(UUID entityUUID, World world) {
		Map<BlockPos, ServerWorld> location = new HashMap<>();

		ServerPlayerEntity player = world.getServer().getPlayerList().getPlayer(entityUUID);
		
		RegistryKey<World> dim = player.getRespawnDimension();
		ServerWorld serverDim = world.getServer().getLevel(dim);
		BlockPos pos = player.getRespawnPosition();
		if (pos == null) // WIP: figure out how to get player spawn point when removed
			pos = serverDim.getSharedSpawnPos();

		location.put(pos, serverDim);

		return location;
	}
}
