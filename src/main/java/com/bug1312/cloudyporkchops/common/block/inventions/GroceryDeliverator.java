package com.bug1312.cloudyporkchops.common.block.inventions;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.common.block.TileEntityBaseBlock;
import com.bug1312.cloudyporkchops.common.tile.inventions.TileGroceryDeliverator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class GroceryDeliverator extends TileEntityBaseBlock.WaterLoggable {

	private static VoxelShape SHAPE = VoxelShapes.or(VoxelShapes.box(1/16D, 0, 1/16D, 15/16D, 4/16D, 15/16D));
	private static VoxelShape PORTAL_SHAPE = VoxelShapes.or(VoxelShapes.box(1/16D, 0, 7.5/16D, 15/16D, 31/16D, 8.5/16D));
	private static VoxelShape PORTAL_SHAPE_ROTATED = VoxelShapes.or(VoxelShapes.box(7.5/16D, 0, 1/16D, 8.5/16D, 31/16D, 15/16D));

	public GroceryDeliverator(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {	
		if(world.getBlockEntity(pos) != null 
				&& ((TileGroceryDeliverator) world.getBlockEntity(pos)).getExitPos() != null 
				&& state.getValue(BlockStateProperties.POWERED)) return getPortalCollider(state);
		return VoxelShapes.empty();
	}

	public static VoxelShape getPortalCollider(BlockState state) {
		if(state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Axis.X) return PORTAL_SHAPE_ROTATED;
		return PORTAL_SHAPE;
	}
		
	@Override @SuppressWarnings("deprecation")
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block u_1, BlockPos u_2, boolean u_3) {
		world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.POWERED, isPowered(pos, world)));
		super.neighborChanged(state, world, pos, u_1, u_2, u_3);
	}
	
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context)
				.setValue(BlockStateProperties.HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis())
				.setValue(BlockStateProperties.POWERED, false);
	}
	
	private boolean isPowered(BlockPos pos, World world) {
		return world.hasNeighborSignal(pos) && ((TileGroceryDeliverator)world.getBlockEntity(pos)).getExitPos() != null;
	}
	
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.HORIZONTAL_AXIS);
		builder.add(BlockStateProperties.POWERED);
	}
	
	public BlockState rotate(BlockState state, Rotation rotation) {
		switch(state.getValue(BlockStateProperties.HORIZONTAL_AXIS)) {
	        case Z:  return state.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X);
	        case X:  return state.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.Z);
	        default: return state;
	    }
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader world, BlockPos pos) {
		return true;
	}	

}
