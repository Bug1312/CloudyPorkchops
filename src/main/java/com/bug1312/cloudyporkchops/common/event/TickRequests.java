package com.bug1312.cloudyporkchops.common.event;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class TickRequests {

	public static Map<Entity, Map<BlockPos, ServerWorld>> TELEPORT_REQUESTS = new HashMap<>();

}
