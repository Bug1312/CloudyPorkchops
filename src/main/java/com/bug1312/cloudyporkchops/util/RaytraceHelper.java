package com.bug1312.cloudyporkchops.util;

import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class RaytraceHelper {

	public static EntityRayTraceResult getHitResult(LivingEntity lookingEntity, double range) {
		float playerRotX = lookingEntity.xRot;
		float playerRotY = lookingEntity.yRot;
		Vector3d startPos = lookingEntity.getEyePosition(1.0F);
		double f2 = Math.cos(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
		double f3 = Math.sin(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
		double f4 = -Math.cos(-playerRotX * ((float) Math.PI / 180F));
		double additionY = Math.sin(-playerRotX * ((float) Math.PI / 180F));
		double additionX = f3 * f4;
		double additionZ = f2 * f4;
		Vector3d endVec = startPos.add((double) additionX * range, (double) additionY * range, (double) additionZ * range);
		AxisAlignedBB startEndBox = new AxisAlignedBB(startPos, endVec);
		Entity lookedAtEntity = null;
		for (Entity ent : lookingEntity.level.getEntities(lookingEntity, startEndBox, (val) -> true)) {
			AxisAlignedBB aabb = ent.getBoundingBox().inflate(ent.getPickRadius());
			Optional<Vector3d> optional = aabb.clip(startPos, endVec);
			if (aabb.contains(startPos)) {
				if (range >= 0.0D) {
					lookedAtEntity = ent;
					startPos = optional.orElse(startPos);
					range = 0.0D;
				}
			} else if (optional.isPresent()) {
				Vector3d vec31 = optional.get();
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

		return (lookedAtEntity == null) ? null : new EntityRayTraceResult(lookedAtEntity);
	}

	@SuppressWarnings("resource")
	public static BlockRayTraceResult getPlayerBlockResult(LivingEntity player, double range) {
		if(!(player instanceof PlayerEntity) || !player.level.isClientSide) return null;
		RayTraceResult rt = Minecraft.getInstance().hitResult;
		
		if(rt instanceof BlockRayTraceResult) return (BlockRayTraceResult) rt;
		return null;
		
	}

}
