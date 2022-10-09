package com.bug1312.cloudyporkchops.common.block.invention;

import com.bug1312.cloudyporkchops.common.init.CloudyItems;
import com.bug1312.cloudyporkchops.common.init.CloudyTiles;
import com.bug1312.cloudyporkchops.common.item.invention.GroceryDeliveratorItem;
import com.bug1312.cloudyporkchops.common.tile.invention.GroceryDeliveratorTile;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;
import com.bug1312.cloudyporkchops.util.helpers.PlayerSpawnHelper;
import com.bug1312.cloudyporkchops.util.helpers.PlayerSpawnHelper.Location;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GroceryDeliverator extends BaseEntityBlock implements EntityBlock, SimpleWaterloggedBlock {

	private static VoxelShape SHAPE = Shapes.or(Shapes.box(0, 0, 0, 1, 4 / 16D, 1));
	private static VoxelShape PORTAL_SHAPE = Shapes.or(Shapes.box(1/16D, 0, 7.5/16D, 15/16D, 24/16D, 8.5/16D));
	private static VoxelShape PORTAL_SHAPE_ROTATED = Shapes.or(Shapes.box(7.5/16D, 0, 1/16D, 8.5/16D, 24/16D, 15/16D));
	public static int PORTAL_TOP_UPDATE_FLAG = (Block.UPDATE_INVISIBLE | Block.UPDATE_CLIENTS);

	public GroceryDeliverator(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState()
				.setValue(BlockStateProperties.POWERED, false)
				.setValue(BlockStateProperties.HALF, Half.BOTTOM));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		if (state.getValue(BlockStateProperties.HALF) == Half.TOP) return Shapes.empty();
		return SHAPE;
	}

	@Override
	public boolean collisionExtendsVertically(BlockState state, BlockGetter level, BlockPos pos, Entity collidingEntity) {
		return state.getValue(BlockStateProperties.HALF) == Half.TOP;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		BlockPos entityPos = pos;
		if (state.getValue(BlockStateProperties.HALF) == Half.TOP) entityPos = entityPos.below();
		if (world.getBlockEntity(entityPos) != null && ((GroceryDeliveratorTile) world.getBlockEntity(entityPos)).isActivated())
			return getPortalCollider(state);
		return Shapes.empty();
	}

	public static VoxelShape getPortalCollider(BlockState state) {
		if (state.getValue(BlockStateProperties.HORIZONTAL_AXIS) == Axis.X) return PORTAL_SHAPE_ROTATED;
		return PORTAL_SHAPE;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
		if (!world.isClientSide && entity instanceof Player && state.getValue(BlockStateProperties.HALF) == Half.BOTTOM) {
			ServerPlayer player = entity.getServer().getPlayerList().getPlayer(entity.getUUID());
			Location location = PlayerSpawnHelper.getSpawnLocation(entity.getUUID(), entity.level);

			CompoundTag nbt = world.getBlockEntity(pos).serializeNBT();
			nbt.putString(CloudyNBTKeys.OWNER, player.getStringUUID());
			nbt.put(CloudyNBTKeys.EXIT_PORTAL_POS, NbtUtils.writeBlockPos(location.pos));
			nbt.putString(CloudyNBTKeys.EXIT_PORTAL_DIM, location.dim);

			if (stack.getItem() instanceof GroceryDeliveratorItem) {
				CompoundTag itemNBT = stack.getOrCreateTag();
				if (GroceryDeliveratorItem.hasLocation(itemNBT)) {
					nbt.remove(CloudyNBTKeys.OWNER);
					nbt.put(CloudyNBTKeys.EXIT_PORTAL_POS, itemNBT.get(CloudyNBTKeys.EXIT_PORTAL_POS));
					nbt.putString(CloudyNBTKeys.EXIT_PORTAL_DIM, itemNBT.getString(CloudyNBTKeys.EXIT_PORTAL_DIM));
				}
			}

			world.getBlockEntity(pos).deserializeNBT(nbt);

			if (isPowered(pos, world)) addTop(state, world, pos);
		}
		super.setPlacedBy(world, pos, state, entity, stack);
	}

	@Override @SuppressWarnings("deprecation")
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState replacedState, boolean u_0) {
		if (state.getValue(BlockStateProperties.HALF) == Half.BOTTOM && isPowered(pos, world)) addTop(state, world, pos);
		super.onPlace(state, world, pos, replacedState, u_0);
	}

	@Override @SuppressWarnings("deprecation")
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block u_1, BlockPos u_2, boolean u_3) {
		super.neighborChanged(state, world, pos, u_1, u_2, u_3);
		if (state.getValue(BlockStateProperties.HALF) == Half.TOP) return;
		boolean flag = isPowered(pos, world);
		world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.POWERED, flag));

		if (flag) addTop(state, world, pos);
		else removeTop(state, world, pos);
	}

	private static void makeParticle(BlockState state, Level world, BlockPos pos, float size) {
		world.addParticle(DustParticleOptions.REDSTONE, pos.getX() + 0.5, pos.getY() - 11/16D, pos.getZ() + 0.5, 0, 0, 0);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if (type == CloudyTiles.GROCERY_DELIVERATOR.get() && state.getOptionalValue(BlockStateProperties.HALF).get() == Half.BOTTOM) return createTickerHelper(type, CloudyTiles.GROCERY_DELIVERATOR.get(), GroceryDeliveratorTile::tick);
		return null;
	}

	@Override @OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(state, world, pos, random);

		if (state.getValue(BlockStateProperties.POWERED) && random.nextFloat() < 0.25F) {
			makeParticle(state, world, pos, 0.5F);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context)
				.setValue(BlockStateProperties.HORIZONTAL_AXIS, context.getHorizontalDirection().getAxis())
				.setValue(BlockStateProperties.POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
	}

	private boolean isPowered(BlockPos pos, Level world) {
		if (world.getBlockState(pos).getValue(BlockStateProperties.HALF) == Half.TOP) return true;
		return (world.hasNeighborSignal(pos)
				&& ((GroceryDeliveratorTile) world.getBlockEntity(pos)).getExitPos() != null
				&& ((GroceryDeliveratorTile) world.getBlockEntity(pos)).getExitDim() != null
				&& (world.getBlockState(pos.above()).getBlock() == Blocks.AIR
					|| world.getBlockState(pos.above()).getBlock() == Blocks.WATER
					|| (world.getBlockState(pos.above()).getBlock() == this
						&& world.getBlockState(pos.above()).getValue(BlockStateProperties.HALF) == Half.TOP)));
	}

	public void addTop(BlockState state, Level world, BlockPos pos) {
		if (state.getValue(BlockStateProperties.HALF) == Half.TOP || world.getBlockState(pos.above()).getBlock() == this) return;
		boolean waterlogged = world.getBlockState(pos.above()).getBlock() == Blocks.WATER && world.getBlockState(pos.above()).getFluidState().isSource();
		BlockState newState = state
				.setValue(BlockStateProperties.HALF, Half.TOP)
				.setValue(BlockStateProperties.WATERLOGGED, waterlogged);
		world.setBlock(pos.above(), newState, PORTAL_TOP_UPDATE_FLAG);
	}

	public void removeTop(BlockState state, Level world, BlockPos pos) {
		if (state.getValue(BlockStateProperties.HALF) == Half.TOP) return;
		if (state.getValue(BlockStateProperties.POWERED) && world.getBlockState(pos.above()).getBlock() == this){
			Block replacement = world.getBlockState(pos.above()).getValue(BlockStateProperties.WATERLOGGED)? Blocks.WATER: Blocks.AIR;
			world.setBlock(pos.above(), replacement.defaultBlockState(), PORTAL_TOP_UPDATE_FLAG);
		}
	}

	@Override @SuppressWarnings("deprecation")
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean u_0) {
		removeTop(state, world, pos);
		super.onRemove(state, world, pos, newState, u_0);
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(CloudyItems.GROCERY_DELIVERATOR.get());
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.HORIZONTAL_AXIS);
		builder.add(BlockStateProperties.WATERLOGGED);
		builder.add(BlockStateProperties.POWERED);
		builder.add(BlockStateProperties.HALF);
	}

	public BlockState rotate(BlockState state, Rotation rotation) {
		switch(state.getValue(BlockStateProperties.HORIZONTAL_AXIS)) {
	        case Z:  return state.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.X);
	        case X:  return state.setValue(BlockStateProperties.HORIZONTAL_AXIS, Direction.Axis.Z);
	        default: return state;
	    }
	}

	@Override
	public RenderShape getRenderShape(BlockState p_49232_) {
		return RenderShape.MODEL;
	}

	// TODO: ONLY MAKE BOTTOM BLOCK REACT / HAVE TILE
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//		return (state.getValue(BlockStateProperties.HALF) == Half.BOTTOM);
		return CloudyTiles.GROCERY_DELIVERATOR.get().create(pos, state);
	}


	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
		return true;
	}

}
