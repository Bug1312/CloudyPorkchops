package com.bug1312.cloudyporkchops.common.tile.invention;

import java.util.UUID;

import com.bug1312.cloudyporkchops.client.init.CloudyOverlays;
import com.bug1312.cloudyporkchops.common.block.invention.GroceryDeliverator;
import com.bug1312.cloudyporkchops.common.event.TickRequests;
import com.bug1312.cloudyporkchops.common.init.CloudyParticles;
import com.bug1312.cloudyporkchops.common.init.CloudyTiles;
import com.bug1312.cloudyporkchops.util.consts.CloudyDamageSources;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;
import com.bug1312.cloudyporkchops.util.helpers.IsFoodHelper;
import com.bug1312.cloudyporkchops.util.helpers.PlayerSpawnHelper.Location;
import com.bug1312.cloudyporkchops.util.helpers.SecondsToTickHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class GroceryDeliveratorTile extends BlockEntity {

	private BlockPos exitPos;
	private UUID ownerUUID;
	private String exitDim;
	private boolean boolForParticles = false;

	public GroceryDeliveratorTile(BlockPos pos, BlockState state) {
		super(CloudyTiles.GROCERY_DELIVERATOR.get(), pos, state);
	}

	public BlockPos getExitPos() { return exitPos; }
	public String getExitDim() { return exitDim; }
	public UUID getOwner() { return ownerUUID; }

	public boolean isActivated() {
		boolean output = getBlockState().getValue(BlockStateProperties.POWERED);

		if (output != boolForParticles) {
			if (output && getBlockState().getValue(BlockStateProperties.HALF) == Half.BOTTOM && !level.isClientSide) {
				ParticleOptions particle = ParticleTypes.ENCHANT;
				if (serializeNBT().contains(CloudyNBTKeys.OWNER)) particle = CloudyParticles.BED.get();

				level.getServer().getLevel(level.dimension()).sendParticles(particle, getBlockPos().getX() + 0.5, getBlockPos().getY() - 1, getBlockPos().getZ() + 0.5, 100, 0, 2, 0, 1);
			}
			boolForParticles = output;
		}
		return output;
	}

	public boolean isLargerPortal() {
		BlockPos otherPos = this.getBlockPos().above().above();
		VoxelShape portalCollision = Shapes.create(GroceryDeliverator.getPortalCollider(getBlockState()).bounds());
		VoxelShape blockCollisions = level.getBlockState(otherPos).getCollisionShape(level, otherPos).move(0, 1, 0);
		boolean output = !Shapes.joinIsNotEmpty(blockCollisions, portalCollision, BooleanOp.AND);

		return output;
	}

	public static void tick(Level level, BlockPos pos, BlockState state, GroceryDeliveratorTile tile) {
		if (tile.isActivated()) {
			AABB aabb = GroceryDeliverator.getPortalCollider(tile.getBlockState()).bounds();
			aabb = aabb.inflate(0.8D/16D).move(tile.getBlockPos().getX(), tile.getBlockPos().getY(), tile.getBlockPos().getZ());
			tile.level.getEntities(null, aabb).forEach(entity -> tile.entityInside(entity));
		}
	}

	public void entityInside(Entity entity) {
		if (entity.isSpectator()) return;
		BlockPos deliveratorPos = this.getBlockPos();
		VoxelShape collisionArea = Shapes.create(GroceryDeliverator.getPortalCollider(getBlockState()).bounds().inflate(0.8D/16D));
		if (Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().move(-deliveratorPos.getX(), -deliveratorPos.getY(), -deliveratorPos.getZ())), collisionArea, BooleanOp.AND)) {
			if (IsFoodHelper.isFood(entity) ) {
				if (!entity.level.isClientSide()) {
					Location location = null;
					ServerPlayer player = level.getServer().getPlayerList().getPlayer(ownerUUID);

					if (ownerUUID != null && player != null) location = new Location(ownerUUID);
					else if (exitPos != null && exitDim != null) location = new Location(exitPos, exitDim);
					else if (exitPos != null) location = new Location(exitPos, level.dimension().toString());

					if (location != null) TickRequests.DELAYED_TELEPORT_REQUESTS.put(entity, location);
				}
			} else {
				if (!(entity instanceof ItemEntity)) entity.hurt(CloudyDamageSources.GROCERY_DELIVERATOR, 2);
				entity.setDeltaMovement(entity.getDeltaMovement().add(getAwayDir(entity).multiply(new Vec3(2,1,2))));

				if (entity.level.isClientSide) {
					Minecraft mc = Minecraft.getInstance();
					if (entity.equals(mc.player)) CloudyOverlays.ELECTRIC_SHOCK.gameTime = (entity.level.getGameTime() + SecondsToTickHelper.toTicks(2));
				}

			}
		}
	}

	private Vec3 getAwayDir(Entity entity) {
		Vec3 entityPos = entity.position();
		double dir = 1;
		double height = 0.3;
		double multiple = 0.8;

		switch(this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_AXIS)) {
			case X: default: {
				if (entityPos.x() < getBlockPos().getX() + 0.5D) dir *= -1;
				return new Vec3(dir , height, 0).multiply(new Vec3(multiple, multiple, multiple));
			}
			case Z: {
				if (entityPos.z() < getBlockPos().getZ() + 0.5D) dir *= -1;
				return new Vec3(0, height, dir / 3D).multiply(new Vec3(multiple, multiple, multiple));
			}
		}
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		if (exitPos != null) nbt.put(CloudyNBTKeys.EXIT_PORTAL_POS, NbtUtils.writeBlockPos(exitPos));
		if (exitDim != null) nbt.putString(CloudyNBTKeys.EXIT_PORTAL_DIM, exitDim);
		if (ownerUUID != null) nbt.putString(CloudyNBTKeys.OWNER, ownerUUID.toString());
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		if (nbt.contains(CloudyNBTKeys.EXIT_PORTAL_POS)) exitPos = NbtUtils.readBlockPos(nbt.getCompound(CloudyNBTKeys.EXIT_PORTAL_POS));
		if (nbt.contains(CloudyNBTKeys.EXIT_PORTAL_DIM)) exitDim = nbt.getString(CloudyNBTKeys.EXIT_PORTAL_DIM);
		if (nbt.contains(CloudyNBTKeys.OWNER)) ownerUUID = UUID.fromString(nbt.getString(CloudyNBTKeys.OWNER));
	}

}
