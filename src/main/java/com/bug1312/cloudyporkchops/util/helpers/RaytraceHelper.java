package com.bug1312.cloudyporkchops.util.helpers;

import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class RaytraceHelper {

	public static EntityHitResult getHitResult(LivingEntity lookingEntity, double range) {
		float playerRotX = lookingEntity.getXRot();
		float playerRotY = lookingEntity.getYRot();
		Vec3 startPos = lookingEntity.getEyePosition(1.0F);
		double f2 = Math.cos(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
		double f3 = Math.sin(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
		double f4 = -Math.cos(-playerRotX * ((float) Math.PI / 180F));
		double additionY = Math.sin(-playerRotX * ((float) Math.PI / 180F));
		double additionX = f3 * f4;
		double additionZ = f2 * f4;
		Vec3 endVec = startPos.add((double) additionX * range, (double) additionY * range, (double) additionZ * range);
		AABB startEndBox = new AABB(startPos, endVec);
		Entity lookedAtEntity = null;
		for (Entity ent : lookingEntity.level.getEntities(lookingEntity, startEndBox, (val) -> true)) {
			AABB aabb = ent.getBoundingBox().inflate(ent.getPickRadius());
			Optional<Vec3> optional = aabb.clip(startPos, endVec);
			if (aabb.contains(startPos)) {
				if (range >= 0.0D) {
					lookedAtEntity = ent;
					startPos = optional.orElse(startPos);
					range = 0.0D;
				}
			} else if (optional.isPresent()) {
				Vec3 vec31 = optional.get();
				double d1 = startPos.distanceToSqr(vec31);
				if (d1 < range || range == 0.0D) {
					if (ent.getRootVehicle() == lookingEntity.getRootVehicle() && !ent.canRiderInteract()) {
						if (range == 0.0D) {
							lookedAtEntity = ent;
							startPos = vec31;
						}
					} else {
						lookedAtEntity = ent;
						startPos = vec31;
						range = d1;
					}
				}
			}
		}

		return (lookedAtEntity == null) ? null : new EntityHitResult(lookedAtEntity);
	}

	@SuppressWarnings("resource")
	public static BlockHitResult getPlayerBlockResult(LivingEntity player, double range) {
		if (!(player instanceof Player) || !player.level.isClientSide) return null;
		HitResult rt = Minecraft.getInstance().hitResult;

		if (rt instanceof BlockHitResult) return (BlockHitResult) rt;
		return null;

	}

}
