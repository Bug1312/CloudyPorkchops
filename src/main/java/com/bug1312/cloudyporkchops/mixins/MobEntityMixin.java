package com.bug1312.cloudyporkchops.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.bug1312.cloudyporkchops.common.init.CloudyTags;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;
import com.bug1312.cloudyporkchops.util.mixininterfaces.IMobEntityMixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity implements IMobEntityMixin {

	protected MobEntityMixin(EntityType<? extends Mob> type, Level world) { super(type, world); }

	private static final EntityDataAccessor<Boolean> DATA_SPRAYED = SynchedEntityData.defineId(MobEntityMixin.class, EntityDataSerializers.BOOLEAN);
	private int remainingSprayTicks = -1;

	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/world/entity/Mob;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
	private void addAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		nbt.putShort(CloudyNBTKeys.SPRAYED_ON, (short)remainingSprayTicks);
	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/world/entity/Mob;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
	private void readAdditionalSaveData(CompoundTag nbt, CallbackInfo info) {
		remainingSprayTicks = nbt.getShort(CloudyNBTKeys.SPRAYED_ON);
	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/world/entity/Mob;baseTick()V")
	private void baseTick(CallbackInfo info) {
		if (this.level.isClientSide || sprayImmune()) remainingSprayTicks = 0;
		if (remainingSprayTicks >= 0) remainingSprayTicks -= 1;

		if (!this.level.isClientSide) setSprayedOn(remainingSprayTicks > 0);
	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/minecraft/world/entity/Mob;defineSynchedData()V")
	private void defineSynchedData(CallbackInfo info) {
		this.entityData.define(DATA_SPRAYED, false);
	}

	@Override
	protected boolean isImmobile() {
		return super.isImmobile() || isSprayedOn();
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		setSprayedOn((nbt.getShort(CloudyNBTKeys.SPRAYED_ON) > 0));
	}

	public final void setSprayedOn(boolean flag) {
		this.entityData.set(DATA_SPRAYED, flag);
	}

	public boolean sprayImmune() {
		return this.getType().is(CloudyTags.EntityTypes.CANT_SPRAY);
	}

	public boolean isSprayedOn() {
		boolean flag = this.level != null && this.level.isClientSide;
		return !this.sprayImmune() && (this.remainingSprayTicks > 0 || (flag && this.entityData.get(DATA_SPRAYED).booleanValue()));
	}

}
