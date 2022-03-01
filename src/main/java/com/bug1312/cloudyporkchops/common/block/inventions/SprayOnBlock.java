package com.bug1312.cloudyporkchops.common.block.inventions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
	public VoxelShape getShape(BlockState state, IBlockReader block, BlockPos pos, ISelectionContext context) {
		VoxelShape outputShape = VoxelShapes.empty();
		
		if(state.getValue(UP)   ) outputShape = VoxelShapes.or(outputShape, VoxelShapes.box(0, 15/16D, 0, 1, 1, 1));
		if(state.getValue(DOWN) ) outputShape = VoxelShapes.or(outputShape, VoxelShapes.box(0, 0, 0, 1, 1/16D, 1) );
		if(state.getValue(NORTH)) outputShape = VoxelShapes.or(outputShape, VoxelShapes.box(0, 0, 0, 1, 1, 1/16D) );
		if(state.getValue(EAST) ) outputShape = VoxelShapes.or(outputShape, VoxelShapes.box(15/16D, 0, 0, 1, 1, 1));
		if(state.getValue(SOUTH)) outputShape = VoxelShapes.or(outputShape, VoxelShapes.box(0, 0, 15/16D, 1, 1, 1));
		if(state.getValue(WEST) ) outputShape = VoxelShapes.or(outputShape, VoxelShapes.box(0, 0, 0, 1/16D, 1, 1) );

		return outputShape;
	}
	
	
	@SuppressWarnings("deprecation") @OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state1, BlockState state2, Direction face) {
		return state2.is(this) ? true : super.skipRendering(state1, state2, face);
	}
}
