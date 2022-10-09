package com.bug1312.cloudyporkchops.common.block.invention;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InvisibleTable extends Block {

	private static VoxelShape SHAPE = Shapes.or(Shapes.box(0, 0, 0, 2/16D, 14/16D, 2/16D), Shapes.box(0, 0, 14/16D, 2/16D, 14/16D, 1), Shapes.box(14/16D, 0, 14/16D, 1, 14/16D, 1), Shapes.box(14/16D, 0, 0, 1, 14/16D, 2/16D), Shapes.box(0, 14/16D, 0, 1, 1, 1));

	public InvisibleTable(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public RenderShape getRenderShape(BlockState p_60550_) {
		return RenderShape.INVISIBLE;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
		return true;
	}
}
