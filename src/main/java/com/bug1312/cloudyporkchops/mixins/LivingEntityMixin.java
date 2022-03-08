package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.bug1312.cloudyporkchops.common.init.CloudyTags;
import com.bug1312.cloudyporkchops.util.statics.CloudyNBTKeys;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;


// WIP
// Current Issues: Will not inject methods, prob simple fix

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	private int remainingSprayTicks = -1;
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/LivingEntity;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V")
	private void addAdditionalSaveData(CallbackInfo info, CompoundNBT nbt) {
		System.out.println("putting");
		nbt.putInt(CloudyNBTKeys.SPRAYED_ON, remainingSprayTicks);
	}
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/LivingEntity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V")
	private void readAdditionalSaveData(CallbackInfo info, CompoundNBT nbt) {
		System.out.println("reading");
		remainingSprayTicks = nbt.getInt(CloudyNBTKeys.SPRAYED_ON);
	}
	
	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/entity/LivingEntity;baseTick()V")
	private void baseTick(CallbackInfo info) {
		LivingEntity entity = (LivingEntity) ((Object) this);
		
		if (entity.level.isClientSide || CloudyTags.EntityTypes.CANT_SPRAY.contains(entity.getType())) remainingSprayTicks = 0;
		if (remainingSprayTicks > 0) remainingSprayTicks -= 1;
	}
	
}
