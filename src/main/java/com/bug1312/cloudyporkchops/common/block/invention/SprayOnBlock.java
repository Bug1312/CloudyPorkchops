package com.bug1312.cloudyporkchops.common.block.invention;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SprayOnBlock extends Block implements SimpleWaterloggedBlock {

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

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter block, BlockPos pos, CollisionContext context) {
		VoxelShape outputShape = Shapes.empty();

		if (state.getValue(UP)   ) outputShape = Shapes.or(outputShape, Shapes.box(0, 15/16D, 0, 1, 1, 1));
		if (state.getValue(DOWN) ) outputShape = Shapes.or(outputShape, Shapes.box(0, 0, 0, 1, 1/16D, 1) );
		if (state.getValue(NORTH)) outputShape = Shapes.or(outputShape, Shapes.box(0, 0, 0, 1, 1, 1/16D) );
		if (state.getValue(EAST) ) outputShape = Shapes.or(outputShape, Shapes.box(15/16D, 0, 0, 1, 1, 1));
		if (state.getValue(SOUTH)) outputShape = Shapes.or(outputShape, Shapes.box(0, 0, 15/16D, 1, 1, 1));
		if (state.getValue(WEST) ) outputShape = Shapes.or(outputShape, Shapes.box(0, 0, 0, 1/16D, 1, 1) );

		if (outputShape.equals(Shapes.empty())) return Shapes.block();
		return outputShape;
	}


	@SuppressWarnings("deprecation") @OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state1, BlockState state2, Direction face) {
		return state2.is(this) ? true : super.skipRendering(state1, state2, face);
	}
}
