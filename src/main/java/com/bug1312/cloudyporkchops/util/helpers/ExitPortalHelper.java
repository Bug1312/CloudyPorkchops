package com.bug1312.cloudyporkchops.util.helpers;

import java.util.List;

import com.bug1312.cloudyporkchops.common.entity.ExitDeliveratorPortal;
import com.bug1312.cloudyporkchops.common.init.CloudyEntities;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ExitPortalHelper {	
	public static void summonPortal(ServerWorld world, BlockPos pos) {
		if(!world.isLoaded(pos)) return;
		
		CompoundNBT portalNBT = new CompoundNBT();
		portalNBT.putString(CloudyNBTKeys.ID, CloudyEntities.EXIT_PORTAL.get().getRegistryName().toString());

		Entity portalEntity = EntityType.loadEntityRecursive(portalNBT, world, (e) -> {
			e.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
			return e;
		});
		
		world.addFreshEntity(portalEntity);
	}
	
	public static void summonOrExtendPortal(ServerWorld world, BlockPos pos) {
		List<Entity> entities = world.getEntitiesOfClass(ExitDeliveratorPortal.class, new AxisAlignedBB(pos));
		if (entities.size() > 0) {
			ExitDeliveratorPortal entity = (ExitDeliveratorPortal) entities.get(0);
			
			entity.timeToDie = world.getGameTime() + SecondsToTickHelper.toTicks(4);
		} else summonPortal(world, pos);
	}
	
}
