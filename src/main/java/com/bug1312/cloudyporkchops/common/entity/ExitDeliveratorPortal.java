package com.bug1312.cloudyporkchops.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.world.World;

public class ExitDeliveratorPortal extends Entity {
	
	public ExitDeliveratorPortal(EntityType<?> entity, World world) {
		super(entity, world);
	}

	@Override
	protected void defineSynchedData() {		
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT nbt) {	
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT nbt) {		
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return new SSpawnObjectPacket(this, this.getId());
	}
	
	

}
