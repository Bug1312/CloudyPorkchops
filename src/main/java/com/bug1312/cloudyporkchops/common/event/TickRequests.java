package com.bug1312.cloudyporkchops.common.event;

import java.util.HashMap;
import java.util.Map;

import com.bug1312.cloudyporkchops.util.PlayerSpawnHelper.Location;

import net.minecraft.entity.Entity;
import net.minecraft.world.server.ServerWorld;

public class TickRequests {

	public static Map<Entity, Location> TELEPORT_REQUESTS = new HashMap<>();
	public static Map<Entity, Map<ServerWorld, Integer>> TELEPORT_REQUESTS_CONFIRMED = new HashMap<>();

}
