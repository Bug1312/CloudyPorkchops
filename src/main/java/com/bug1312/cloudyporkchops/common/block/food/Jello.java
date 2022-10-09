package com.bug1312.cloudyporkchops.common.block.food;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Jello extends SlimeBlock {

	private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.9D, 1.0D);

	public Jello(Properties properties) {
		super(properties);
	}

	@Override @SuppressWarnings("deprecation")
	public VoxelShape getCollisionShape(BlockState state, BlockGetter block, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext) {
			EntityCollisionContext entitycollisioncontext = (EntityCollisionContext) context;
			Entity entity = entitycollisioncontext.getEntity();
			if (entity != null) {
				if (entity.fallDistance > 2.5F) {
					return FALLING_COLLISION_SHAPE;
				}

				boolean flag = entity instanceof FallingBlockEntity;
				if (flag || context.isAbove(Shapes.block(), pos, false) && !context.isDescending()) {
					return super.getCollisionShape(state, block, pos, context);
				}
			}
		}

		return Shapes.empty();
	}

	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float speed) {}

	@Override
	public void updateEntityAfterFallOn(BlockGetter world, Entity entity) {
		if (world.getBlockState(entity.blockPosition()).getBlock() instanceof Jello) return;
		super.updateEntityAfterFallOn(world, entity);
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter block, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}

	@Override
	public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
//		if (entity.getDeltaMovement().y < 0) return false; // Must check game feel
		return true;
	}

}
