package com.bug1312.cloudyporkchops.common.entity;

import com.bug1312.cloudyporkchops.util.helpers.SecondsToTickHelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class ExitDeliveratorPortal extends Entity {

	public long timeToDie;

	public ExitDeliveratorPortal(EntityType<?> entity, Level world) {
		super(entity, world);
	}

	@Override public void defineSynchedData() {}
	@Override public void readAdditionalSaveData(CompoundTag nbt) {}
	@Override public void addAdditionalSaveData(CompoundTag nbt) {}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level.isClientSide && level.getGameTime() > timeToDie) remove(RemovalReason.DISCARDED);
	}

	@Override
	public void onAddedToWorld() {
		super.onAddedToWorld();
		timeToDie = level.getGameTime() + SecondsToTickHelper.toTicks(4);
	}

}
