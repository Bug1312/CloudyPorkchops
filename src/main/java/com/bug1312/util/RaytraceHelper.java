package com.bug1312.util;

import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class RaytraceHelper {

	public static EntityRayTraceResult getHitResult(LivingEntity player, double range) {
		float playerRotX = player.xRot;
		float playerRotY = player.yRot;
		Vector3d startPos = player.getEyePosition(1.0F);
		double f2 = Math.cos(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
		double f3 = Math.sin(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
		double f4 = -Math.cos(-playerRotX * ((float) Math.PI / 180F));
		double additionY = Math.sin(-playerRotX * ((float) Math.PI / 180F));
		double additionX = f3 * f4;
		double additionZ = f2 * f4;
		Vector3d endVec = startPos.add((double) additionX * range, (double) additionY * range, (double) additionZ * range);
		AxisAlignedBB startEndBox = new AxisAlignedBB(startPos, endVec);
		Entity entity = null;
		for (Entity entity1 : player.level.getEntities(player, startEndBox, (val) -> true)) {
			AxisAlignedBB aabb = entity1.getBoundingBox().inflate(entity1.getPickRadius());
			Optional<Vector3d> optional = aabb.clip(startPos, endVec);
			if (aabb.contains(startPos)) {
				if (range >= 0.0D) {
					entity = entity1;
					startPos = optional.orElse(startPos);
					range = 0.0D;
				}
			} else if (optional.isPresent()) {
				Vector3d vec31 = optional.get();
				double d1 = startPos.distanceToSqr(vec31);
				if (d1 < range || range == 0.0D) {
					if (entity1.getRootVehicle() == player.getRootVehicle() && !entity1.canRiderInteract()) {
						if (range == 0.0D) {
							entity = entity1;
							startPos = vec31;
						}
					} else {
						entity = entity1;
						startPos = vec31;
						range = d1;
					}
				}
			}
		}

		return (entity == null) ? null : new EntityRayTraceResult(entity);
	}

}
