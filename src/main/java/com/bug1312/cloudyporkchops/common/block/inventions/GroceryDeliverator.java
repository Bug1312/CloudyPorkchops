package com.bug1312.cloudyporkchops.common.block.inventions;

import java.util.UUID;
import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.common.block.TileEntityBaseBlock;
import com.bug1312.cloudyporkchops.common.tile.inventions.GroceryDeliveratorTile;
import com.bug1312.cloudyporkchops.util.statics.CloudyNBTKeys;
import com.bug1312.cloudyporkchops.util.statics.CloudyProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.BlockFlags;

public class GroceryDeliverator extends TileEntityBaseBlock.WaterLoggable {

	private static VoxelShape SHAPE = VoxelShapes.or(VoxelShapes.box(1/16D, 0, 1/16D, 15/16D, 4/16D, 15/16D));
	private static VoxelShape PORTAL_SHAPE = VoxelShapes.or(VoxelShapes.box(1/16D, 0, 7.5/16D, 15/16D, 37/16D, 8.5/16D));
	private static VoxelShape PORTAL_SHAPE_ROTATED = VoxelShapes.or(VoxelShapes.box(7.5/16D, 0, 1/16D, 8.5/16D, 37/16D, 15/16D));
	public static int PORTAL_TOP_UPDATE_FLAG = (BlockFlags.BLOCK_UPDATE | BlockFlags.UPDATE_NEIGHBORS);
	
	public GroceryDeliverator(Supplier<TileEntity> tileEntitySupplier, Properties properties) {
		super(tileEntitySupplier, properties);
		registerDefaultState(defaultBlockState()
				.setValue(BlockStateProperties.POWERED, false)
				.setValue(BlockStateProperties.HALF, Half.BOTTOM));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		if(state.getValue(BlockStateProperties.HALF) == Half.TOP) return VoxelShapes.empty();
		return SHAPE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {	
		if(world.getBlockEntity(pos) != null 
				&& ((GroceryDeliveratorTile) world.getBlockEntity(pos)).getExitPos() != null 
				&& state.getValue(BlockStateProperties.POWERED)
				&& (world.getBlockState(pos.above()).getBlock() == Blocks.AIR)
					|| (world.getFluidState(pos.above()).isSource()
						&& world.getBlockState(pos.above()).getBlock() == Blocks.WATER)) 
			return getPortalCollider(state);
		return VoxelShapes.empty();
	}

	public static VoxelShape getPortalCollider(BlockState state) {
		if(state.getValue(BlockStateProperties.HALF) == Half.TOP) return VoxelShapes.empty();
		if(state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Axis.X) return PORTAL_SHAPE_ROTATED;
		return PORTAL_SHAPE;
	}
		
	public void updatePortalSize(BlockState thisState, World world, BlockPos thisPos) {
		boolean isTop = (thisState.getValue(BlockStateProperties.HALF) == Half.TOP);
		BlockPos pos = isTop ? thisPos.below() : thisPos;
		BlockPos otherPos = pos.above().above();
		BlockState state = world.getBlockState(pos);
		
		VoxelShape portalCollision = VoxelShapes.create(getPortalCollider(state).bounds());
		VoxelShape blockCollisions = world.getBlockState(otherPos).getCollisionShape(world, otherPos).move(0, 2, 0);				
		boolean output = !VoxelShapes.joinIsNotEmpty(blockCollisions, portalCollision, IBooleanFunction.AND);

		if(state.getValue(CloudyProperties.TALL_PORTAL) != output) {
			world.setBlock(pos, state.setValue(CloudyProperties.TALL_PORTAL, output), GroceryDeliverator.PORTAL_TOP_UPDATE_FLAG);

		}
	}
	
	@Override @SuppressWarnings("deprecation")
	public void onPlace(BlockState state, World world, BlockPos pos, BlockState replacedState, boolean u_0) {
		if(state.getValue(BlockStateProperties.HALF) == Half.BOTTOM) {
			UUID uuid = Minecraft.getInstance().player.getUUID();
			System.out.println(uuid);
			ServerPlayerEntity player = world.getServer().getPlayerList().getPlayer(uuid);
			
			RegistryKey<World> exitDim = player.getRespawnDimension();
			BlockPos exitPos = player.getRespawnPosition();
			if(exitPos == null) exitPos = world.getServer().getLevel(player.getRespawnDimension()).getSharedSpawnPos();
			
			CompoundNBT nbt = world.getBlockEntity(pos).serializeNBT();
			nbt.put(CloudyNBTKeys.EXIT_PORTAL_POS, NBTUtil.writeBlockPos(exitPos));
			nbt.putString(CloudyNBTKeys.EXIT_PORTAL_DIM, exitDim.location().toString());
			world.getBlockEntity(pos).deserializeNBT(nbt);
		
			if(isPowered(pos, world)) addTop(state, world, pos);

		}
		
		super.onPlace(state, world, pos, replacedState, u_0);
	}
	
	@Override @SuppressWarnings("deprecation")
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block u_1, BlockPos u_2, boolean u_3) {
		super.neighborChanged(state, world, pos, u_1, u_2, u_3);
		updatePortalSize(state, world, pos);
		if(state.getValue(BlockStateProperties.HALF) == Half.TOP) return;
		boolean flag = isPowered(pos, world);
		world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.POWERED, flag));
		
		if(flag) addTop(state, world, pos); 
		else removeTop(state, world, pos);		
	}
	
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context)
				.setValue(BlockStateProperties.HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis())
				.setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
	}
	
	private boolean isPowered(BlockPos pos, World world) {
		if(world.getBlockState(pos).getValue(BlockStateProperties.HALF) == Half.TOP) return true;
		return (world.hasNeighborSignal(pos) 
				&& ((GroceryDeliveratorTile) world.getBlockEntity(pos)).getExitPos() != null
				&& ((GroceryDeliveratorTile) world.getBlockEntity(pos)).getExitDim() != null
				&& (world.getBlockState(pos.above()).getBlock() == Blocks.AIR 
					|| world.getBlockState(pos.above()).getBlock() == Blocks.WATER
					|| world.getBlockState(pos.above()).getBlock() == this));
	}
	
	public void addTop(BlockState state, World world, BlockPos pos) {
		if(state.getValue(BlockStateProperties.HALF) == Half.TOP || world.getBlockState(pos.above()).getBlock() == this) return;
		boolean waterlogged = world.getBlockState(pos.above()).getBlock() == Blocks.WATER && world.getBlockState(pos.above()).getFluidState().isSource();  
		BlockState newState = state
				.setValue(BlockStateProperties.HALF, Half.TOP)
				.setValue(BlockStateProperties.WATERLOGGED, waterlogged);
		world.setBlock(pos.above(), newState, PORTAL_TOP_UPDATE_FLAG);
	}
	
	public void removeTop(BlockState state, World world, BlockPos pos) {
		if(state.getValue(BlockStateProperties.HALF) == Half.TOP) return;
		if(state.getValue(BlockStateProperties.POWERED) && world.getBlockState(pos.above()).getBlock() == this){ 
			Block replacement = world.getBlockState(pos.above()).getValue(BlockStateProperties.WATERLOGGED)? Blocks.WATER: Blocks.AIR;
			world.setBlock(pos.above(), replacement.defaultBlockState(), PORTAL_TOP_UPDATE_FLAG);
		}
	}
	
	@Override @SuppressWarnings("deprecation")
	public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean u_0) {
		removeTop(state, world, pos);
		super.onRemove(state, world, pos, newState, u_0);
	}
	
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.HORIZONTAL_AXIS);
		builder.add(BlockStateProperties.POWERED);
		builder.add(BlockStateProperties.HALF);
		builder.add(CloudyProperties.TALL_PORTAL);
	}
	
	public BlockState rotate(BlockState state, Rotation rotation) {
		switch(state.getValue(BlockStateProperties.HORIZONTAL_AXIS)) {
	        case Z:  return state.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X);
	        case X:  return state.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.Z);
	        default: return state;
	    }
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return (state.getValue(BlockStateProperties.HALF) == Half.BOTTOM);
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return tileEntitySupplier.get();
	}
	
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader world, BlockPos pos) {
		return true;
	}	

}