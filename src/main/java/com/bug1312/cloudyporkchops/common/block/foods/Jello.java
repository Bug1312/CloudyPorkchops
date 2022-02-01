package com.bug1312.cloudyporkchops.common.block.foods;

import com.bug1312.cloudyporkchops.common.init.CloudyBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.EntitySelectionContext;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class Jello extends SlimeBlock {

	private static final VoxelShape FALLING_COLLISION_SHAPE = VoxelShapes.box(0.0D, 0.0D, 0.0D, 1.0D, 0.9D, 1.0D);

	public Jello(Properties properties) {
		super(properties);
	}

	@Override public void fallOn(World world, BlockPos pos, Entity entity, float speed) { }

	@SuppressWarnings("deprecation") @Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader block, BlockPos pos, ISelectionContext context) {
		if (context instanceof EntitySelectionContext) {
			EntitySelectionContext entitycollisioncontext = (EntitySelectionContext) context;
			Entity entity = entitycollisioncontext.getEntity();
			if (entity != null) {
				if (entity.fallDistance > 2.5F) {
					return FALLING_COLLISION_SHAPE;
				}

				boolean flag = entity instanceof FallingBlockEntity;
				if (flag || context.isAbove(VoxelShapes.block(), pos, false) && !context.isDescending()) {
					return super.getCollisionShape(state, block, pos, context);
				}
			}
		}

		return VoxelShapes.empty();
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, IBlockReader block, BlockPos pos, ISelectionContext context) {
		return VoxelShapes.empty();
	}
	
	@Override // Workin on this bit
	public boolean isScaffolding(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
		return true;
	}

	@Override
	public void updateEntityAfterFallOn(IBlockReader world, Entity entity) {
		if (world.getBlockState(entity.blockPosition().above()).getBlock() instanceof Jello) return;
		super.updateEntityAfterFallOn(world, entity);
	}

}
