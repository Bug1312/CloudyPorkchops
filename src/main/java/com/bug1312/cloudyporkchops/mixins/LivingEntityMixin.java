package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.bug1312.cloudyporkchops.common.init.CloudyTags;
import com.bug1312.cloudyporkchops.util.statics.CloudyNBTKeys;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	
	private int remainingSprayTicks = -1;
	private LivingEntity entity = (LivingEntity) (Object) this;

	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/LivingEntity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V")
	private void addAdditionalSaveData(CompoundNBT nbt, CallbackInfo info) {
		nbt.putInt(CloudyNBTKeys.SPRAYED_ON, remainingSprayTicks);
	}
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/LivingEntity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V")
	private void readAdditionalSaveData(CompoundNBT nbt, CallbackInfo info) {
		System.out.println("putting");
		remainingSprayTicks = nbt.getInt(CloudyNBTKeys.SPRAYED_ON);
	}
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/LivingEntity;baseTick()V")
	private void baseTick(CallbackInfo info) {		
		System.out.println("tick");
		if (entity.level.isClientSide || sprayImmune()) remainingSprayTicks = 0;
		if (remainingSprayTicks > 0) remainingSprayTicks -= 1;
	}
	
	private boolean sprayImmune() {
		return CloudyTags.EntityTypes.CANT_SPRAY.contains(entity.getType());
	}
	
	public boolean isSprayedOn() {
		return this.remainingSprayTicks > 0;
//		boolean flag = this.level != null && this.level.isClientSide;
//		return !this.sprayImmune() && (this.remainingSprayTicks > 0 || flag && this.getSharedFlag(0));
	}

	// Necessary constructor for class
//	protected LivingEntityMixin(EntityType<? extends LivingEntity> entity, World world) { super(entity, world); }	
}
