package com.bug1312.cloudyporkchops.common.tile.inventions;

import com.bug1312.cloudyporkchops.common.block.inventions.GroceryDeliverator;
import com.bug1312.cloudyporkchops.common.init.CloudyTiles;
import com.bug1312.cloudyporkchops.util.IsFoodHelper;
import com.bug1312.cloudyporkchops.util.statics.CloudyDamageSources;
import com.bug1312.cloudyporkchops.util.statics.CloudyNBTKeys;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;

public class TileGroceryDeliverator extends TileEntity implements ITickableTileEntity {

	// WIP:
	// Add sounds
	// Rejected clients get screen overlay of lightning
	// Must figure out how to setup location of exitPos (NO GUI ALLOWED)
		// new place method (see Create Weighted Ejector)	-- Non Stackable
		// two teleporters									-- Not representative
		// spawn point (delivers groceries 'home')			-- Who would use normally
		// to player who placed								-- Useless without machines
	
	private BlockPos exitPos;
	
	public TileGroceryDeliverator() {
		super(CloudyTiles.GROCERY_DELIVERATOR.get());
	}

	public BlockPos getExitPos() { return exitPos; }
	public void setExitPos(BlockPos pos) { this.exitPos = pos; }
	
	@Override
	public void tick() {
		if (getBlockState().getValue(BlockStateProperties.POWERED)) {
			AxisAlignedBB aabb = GroceryDeliverator.getPortalCollider(getBlockState()).bounds();
			aabb = aabb.inflate(0.8D/16D).move(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
			this.level.getEntities(null, aabb).forEach(entity -> entityInside(entity));
		}
	}
	
	public void entityInside(Entity entity) {
		if (entity.isSpectator()) return;
		BlockPos pos = this.getBlockPos();
		VoxelShape collisionArea = VoxelShapes.create(GroceryDeliverator.getPortalCollider(getBlockState()).bounds().inflate(0.8D/16D));
		if (VoxelShapes.joinIsNotEmpty(VoxelShapes.create(entity.getBoundingBox().move(-pos.getX(), -pos.getY(), -pos.getZ())), collisionArea, IBooleanFunction.AND)) {
			if (IsFoodHelper.isFood(entity)) {
				entity.teleportTo(exitPos.getX(), exitPos.getY(), exitPos.getZ());
			} else {
				if(!(entity instanceof ItemEntity)) entity.hurt(CloudyDamageSources.GROCERY_DELIVERATOR, 2);
				entity.setDeltaMovement(entity.getDeltaMovement().add(getAwayDir(entity)));
			}
		}
	}

	private Vector3d getAwayDir(Entity entity) {
		Vector3d entityPos = entity.position();
		double dir = 1;
		double height = 0.3;
		double multiple = (entity instanceof LivingEntity) ? 1.5 : 1;
		
		switch(this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_AXIS)) {
			case X: default: {
				if (entityPos.x() < getBlockPos().getX() + 0.5D) dir *= -1;
				return new Vector3d(dir , height, 0).multiply(new Vector3d(multiple, multiple, multiple));
			}
			case Z: {
				if (entityPos.z() < getBlockPos().getZ() + 0.5D) dir *= -1;
				return new Vector3d(0, height, dir / 3D).multiply(new Vector3d(multiple, multiple, multiple));
			}
		}
	}
	
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		if (this.exitPos != null) nbt.put(CloudyNBTKeys.EXIT_PORTAL, NBTUtil.writeBlockPos(this.exitPos));
		return nbt;
	}

	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if (nbt.contains(CloudyNBTKeys.EXIT_PORTAL)) this.exitPos = NBTUtil.readBlockPos(nbt.getCompound(CloudyNBTKeys.EXIT_PORTAL));
	}


}
