package com.bug1312.cloudyporkchops.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExitDeliveratorPortal extends Entity {
	 	 
	public ExitDeliveratorPortal(EntityType<?> entity, World world) {
		super(entity, world);
	}

	@Override
	public void defineSynchedData() {	
	}

	@Override
	public void readAdditionalSaveData(CompoundNBT nbt) {	
	}

	@Override
	public void addAdditionalSaveData(CompoundNBT nbt) {		
	}

	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	
	

}
