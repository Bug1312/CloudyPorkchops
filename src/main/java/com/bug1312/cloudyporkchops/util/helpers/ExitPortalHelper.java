package com.bug1312.cloudyporkchops.util.helpers;

import java.util.List;

import com.bug1312.cloudyporkchops.common.entity.ExitDeliveratorPortal;
import com.bug1312.cloudyporkchops.common.init.CloudyEntities;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;

public class ExitPortalHelper {
	public static void summonPortal(ServerLevel world, BlockPos pos) {
		if (!world.isLoaded(pos)) return;

		CompoundTag portalNBT = new CompoundTag();
		portalNBT.putString(CloudyNBTKeys.ID, ForgeRegistries.ENTITY_TYPES.getKey(CloudyEntities.EXIT_PORTAL.get()).toString());

		Entity portalEntity = EntityType.loadEntityRecursive(portalNBT, world, (e) -> {
			e.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0, 0);
			return e;
		});

		world.addFreshEntity(portalEntity);
	}

	public static void summonOrExtendPortal(ServerLevel world, BlockPos pos) {
		List<ExitDeliveratorPortal> entities = world.getEntitiesOfClass(ExitDeliveratorPortal.class, new AABB(pos));
		if (entities.size() > 0) {
			ExitDeliveratorPortal entity = (ExitDeliveratorPortal) entities.get(0);

			entity.timeToDie = world.getGameTime() + SecondsToTickHelper.toTicks(4);
		} else summonPortal(world, pos);
	}

}
