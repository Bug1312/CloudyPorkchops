package com.bug1312.cloudyporkchops.util.helpers;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class PlayerSpawnHelper {
	public static Location getSpawnLocation(UUID entityUUID, Level world) {
		ServerPlayer player = world.getServer().getPlayerList().getPlayer(entityUUID);

		ResourceKey<Level> dim = player.getRespawnDimension();
		ServerLevel serverDim = world.getServer().getLevel(dim);
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

		public ServerLevel getDimension(Level level) {
			return level.getServer().getLevel(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(dim)));
		}
	}
}
