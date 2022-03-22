package com.bug1312.cloudyporkchops.common.block.invention;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class InvisibleTable extends Block {

	private static VoxelShape SHAPE = VoxelShapes.or(VoxelShapes.box(0, 0, 0, 2/16D, 14/16D, 2/16D), VoxelShapes.box(0, 0, 14/16D, 2/16D, 14/16D, 1), VoxelShapes.box(14/16D, 0, 14/16D, 1, 14/16D, 1), VoxelShapes.box(14/16D, 0, 0, 1, 14/16D, 2/16D), VoxelShapes.box(0, 14/16D, 0, 1, 1, 1));
	
	public InvisibleTable(Properties properties) {
		super(properties);
	}
		
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader world, BlockPos pos) {
		return true;
	}
}
