package com.bug1312.cloudyporkchops.util.helpers;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;

public class DirectionHelper {
	public static BooleanProperty toProperty(Direction direction) {
		switch(direction) {
			case DOWN: 	return BlockStateProperties.DOWN;
			case EAST: 	return BlockStateProperties.EAST;
			case NORTH: return BlockStateProperties.NORTH;
			case SOUTH: return BlockStateProperties.SOUTH;
			case UP: 	return BlockStateProperties.UP;
			case WEST: 	return BlockStateProperties.WEST;
			default: 	return BlockStateProperties.DOWN;
		}
	}
}
