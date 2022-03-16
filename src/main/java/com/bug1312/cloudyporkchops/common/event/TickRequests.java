package com.bug1312.cloudyporkchops.common.event;

import java.util.HashMap;
import java.util.Map;

import com.bug1312.cloudyporkchops.util.PlayerSpawnHelper.Location;

import net.minecraft.entity.Entity;

public class TickRequests {

	public static Map<Entity, Location> TELEPORT_REQUESTS = new HashMap<>();

}
