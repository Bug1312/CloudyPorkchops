package com.bug1312.cloudyporkchops.common.tile.inventions;

import java.util.UUID;

import com.bug1312.cloudyporkchops.common.block.inventions.GroceryDeliverator;
import com.bug1312.cloudyporkchops.common.event.TickRequests;
import com.bug1312.cloudyporkchops.common.init.CloudyTiles;
import com.bug1312.cloudyporkchops.util.IsFoodHelper;
import com.bug1312.cloudyporkchops.util.PlayerSpawnHelper.Location;
import com.bug1312.cloudyporkchops.util.statics.CloudyDamageSources;
import com.bug1312.cloudyporkchops.util.statics.CloudyNBTKeys;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

public class GroceryDeliveratorTile extends TileEntity implements ITickableTileEntity {

	
	// WIP:
	// Add sounds
	// Rejected clients get screen overlay of lightning
	// Redstone particles
	// Must figure out how to setup location of exitPos (NO GUI ALLOWED)
		// new place method (2nd item for placing exit)		-- Non Stackable
		// two teleporters									-- Not representative
		// spawn point (delivers groceries 'home')			-- Who would use normally
	// For some reason NBT wont save
	
	private BlockPos exitPos;
	private ServerWorld exitDim;
	private UUID ownerUUID;
	
	public GroceryDeliveratorTile() {
		super(CloudyTiles.GROCERY_DELIVERATOR.get());
	}

	public BlockPos getExitPos() { return exitPos; }
	public void setExitPos(BlockPos pos) { this.exitPos = pos; }
	
	public ServerWorld getExitDim() { return exitDim; }
	public void setExitDim(ServerWorld pos) { this.exitDim = pos; }
	
	public UUID getOwner() { return ownerUUID; }
	public void setOwner(UUID uuid) { this.ownerUUID = uuid; }
	
	public boolean isActivated() {
		return getBlockState().getValue(BlockStateProperties.POWERED);
	}
	
	public boolean isLargerPortal() {
		BlockPos otherPos = this.getBlockPos().above().above();
		VoxelShape portalCollision = VoxelShapes.create(GroceryDeliverator.getPortalCollider(getBlockState()).bounds());
		VoxelShape blockCollisions = level.getBlockState(otherPos).getCollisionShape(level, otherPos).move(0, 2, 0);				
		boolean output = !VoxelShapes.joinIsNotEmpty(blockCollisions, portalCollision, IBooleanFunction.AND);
		
		return output;
	}	
	
	@Override
	public void tick() {
		if (isActivated()) {
			AxisAlignedBB aabb = GroceryDeliverator.getPortalCollider(getBlockState()).bounds();
			aabb = aabb.inflate(0.8D/16D).move(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ());
			this.level.getEntities(null, aabb).forEach(entity -> entityInside(entity));
		}
	}
	
	public void entityInside(Entity entity) {
		if (entity.isSpectator()) return;
		BlockPos deliveratorPos = this.getBlockPos();
		VoxelShape collisionArea = VoxelShapes.create(GroceryDeliverator.getPortalCollider(getBlockState()).bounds().inflate(0.8D/16D));
		if (VoxelShapes.joinIsNotEmpty(VoxelShapes.create(entity.getBoundingBox().move(-deliveratorPos.getX(), -deliveratorPos.getY(), -deliveratorPos.getZ())), collisionArea, IBooleanFunction.AND)) {
			if (IsFoodHelper.isFood(entity) ) {
				if(!entity.level.isClientSide()) {
					Location location = null;	
					ServerPlayerEntity player = level.getServer().getPlayerList().getPlayer(ownerUUID);
					
					if(ownerUUID != null && player != null) location = new Location(ownerUUID);
					else if(exitPos != null && exitDim != null) location = new Location(exitPos, exitDim.getLevel());
					else if(exitPos != null) location = new Location(exitPos, level.getServer().getLevel(level.dimension()).getLevel());					
					
					if(location != null) TickRequests.TELEPORT_REQUESTS.put(entity, location);						
				}
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
		if (exitPos != null) nbt.put(CloudyNBTKeys.EXIT_PORTAL_POS, NBTUtil.writeBlockPos(exitPos));
		if (exitDim != null) nbt.putString(CloudyNBTKeys.EXIT_PORTAL_DIM, exitDim.dimension().location().toString());
		if (ownerUUID != null) nbt.putString(CloudyNBTKeys.OWNER, ownerUUID.toString());
		return nbt;
	}

	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if (nbt.contains(CloudyNBTKeys.EXIT_PORTAL_POS)) exitPos = NBTUtil.readBlockPos(nbt.getCompound(CloudyNBTKeys.EXIT_PORTAL_POS));
		if (nbt.contains(CloudyNBTKeys.EXIT_PORTAL_DIM)) exitDim = level.getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(nbt.getString(CloudyNBTKeys.EXIT_PORTAL_DIM))));
		if (nbt.contains(CloudyNBTKeys.OWNER)) ownerUUID = UUID.fromString(nbt.getString(CloudyNBTKeys.OWNER));
	}


}
