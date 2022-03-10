package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.bug1312.cloudyporkchops.common.init.CloudyTags;
import com.bug1312.cloudyporkchops.util.mixininterfaces.IMobEntityMixin;
import com.bug1312.cloudyporkchops.util.statics.CloudyNBTKeys;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements IMobEntityMixin {
	
	protected MobEntityMixin(EntityType<? extends LivingEntity> type, World world) { super(type, world); }

	private static final DataParameter<Boolean> DATA_SPRAYED = EntityDataManager.defineId(MobEntityMixin.class, DataSerializers.BOOLEAN);
	private int remainingSprayTicks = -1;
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/MobEntity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V")
	private void addAdditionalSaveData(CompoundNBT nbt, CallbackInfo info) {
		nbt.putShort(CloudyNBTKeys.SPRAYED_ON, (short)remainingSprayTicks);
	}
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/MobEntity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V")
	private void readAdditionalSaveData(CompoundNBT nbt, CallbackInfo info) {
		remainingSprayTicks = nbt.getShort(CloudyNBTKeys.SPRAYED_ON);
		
	}
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/MobEntity;baseTick()V")
	private void baseTick(CallbackInfo info) {		
		if (this.level.isClientSide || sprayImmune()) remainingSprayTicks = 0;
		if (remainingSprayTicks >= 0) remainingSprayTicks -= 1;

		if(!this.level.isClientSide) setSprayedOn(remainingSprayTicks > 0);
	}
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/MobEntity;defineSynchedData()V")
	private void defineSynchedData(CallbackInfo info) {
		this.entityData.define(DATA_SPRAYED, false);
	}
	
	@Override 
	protected boolean isImmobile() {
		return super.isImmobile() || isSprayedOn();
	}
	
	@Override 
	public void load(CompoundNBT nbt) {
		super.load(nbt);
		setSprayedOn((nbt.getShort(CloudyNBTKeys.SPRAYED_ON) > 0));
	}
	
	public final void setSprayedOn(boolean flag) {
		this.entityData.set(DATA_SPRAYED, flag);
	}
	
	public boolean sprayImmune() {
		return CloudyTags.EntityTypes.CANT_SPRAY.contains(this.getType());
	}

	public boolean isSprayedOn() {
		boolean flag = this.level != null && this.level.isClientSide;
		return !this.sprayImmune() && (this.remainingSprayTicks > 0 || (flag && this.entityData.get(DATA_SPRAYED).booleanValue()));
	}	

}
