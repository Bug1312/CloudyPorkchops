package com.bug1312.cloudyporkchops.common.block.inventions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

public class SprayOnBlock extends Block implements IWaterLoggable {

	private static BooleanProperty UP = BlockStateProperties.UP;
	private static BooleanProperty DOWN = BlockStateProperties.DOWN;
	private static BooleanProperty NORTH = BlockStateProperties.NORTH;
	private static BooleanProperty EAST = BlockStateProperties.EAST;
	private static BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	private static BooleanProperty WEST = BlockStateProperties.WEST;
	private static BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public SprayOnBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(UP, false).setValue(DOWN, false)
				.setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false)
				.setValue(WATERLOGGED, false));
	}

	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> state) {
		state.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
	}
	
	@Override
	public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		if (state.getValue(UP) == false && 
				state.getValue(DOWN) == false && 
				state.getValue(NORTH) == false && 
				state.getValue(EAST) == false && 
				state.getValue(SOUTH) == false && 
				state.getValue(WEST) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader block, BlockPos pos,
			ISelectionContext p_220053_4_) {
		VoxelShape up;
		VoxelShape down;
		VoxelShape north;
		VoxelShape east;
		VoxelShape south;
		VoxelShape west;
		
		if(state.getValue(UP)) up = VoxelShapes.box(0, 1, 0, 1, 17/16D, 1);
		else up = VoxelShapes.empty();
		if(state.getValue(DOWN)) down = VoxelShapes.box(0, 0, 0, 1, 1/16D, 1);
		else down = VoxelShapes.empty();
		if(state.getValue(NORTH)) north = VoxelShapes.box(0, 0, 0, 1, 1, 1/16D);
		else north = VoxelShapes.empty();
		if(state.getValue(EAST)) east = VoxelShapes.box(15/16D, 0, 0, 1, 1, 1);
		else east = VoxelShapes.empty();
		if(state.getValue(SOUTH)) south = VoxelShapes.box(0, 0, 15/16D, 1, 1, 1);
		else south = VoxelShapes.empty();
		if(state.getValue(WEST)) west = VoxelShapes.box(0, 0, 0, 1/16D, 1, 1);
		else west = VoxelShapes.empty();

		return VoxelShapes.or(up, down, north, east, south, west);
	}
}
