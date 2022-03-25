package com.bug1312.cloudyporkchops.common.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bug1312.cloudyporkchops.common.item.invention.ShoesCan;
import com.bug1312.cloudyporkchops.util.ExitPortalHelper;
import com.bug1312.cloudyporkchops.util.PlayerSpawnHelper;
import com.bug1312.cloudyporkchops.util.PlayerSpawnHelper.Location;
import com.bug1312.cloudyporkchops.util.consts.CloudyNBTKeys;
import com.bug1312.cloudyporkchops.util.RaytraceHelper;
import com.bug1312.cloudyporkchops.util.SprayEntityHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonBusEvents {

	@SubscribeEvent // EntityInteractSpecific is broken with setCanceled
	public static void stopSprayedEntityInteractions(PlayerInteractEvent event) { 
		EntityRayTraceResult entityLookingAt = RaytraceHelper.getHitResult(event.getPlayer(), event.getPlayer().getAttributeValue(ForgeMod.REACH_DISTANCE.get()));
		if(!(event.getPlayer().getItemInHand(event.getHand()).getItem() instanceof ShoesCan) && entityLookingAt != null && SprayEntityHelper.isEntitySprayedOn(entityLookingAt.getEntity())) { 
			event.setCanceled(true);
		} else if(event.isCancelable()) event.setCanceled(false);
	}
	
	@SubscribeEvent
	public static <T extends BlockPos> void serverTickEvent(ServerTickEvent event) {
		while(TickRequests.DELAYED_TELEPORT_REQUESTS.size() > 0) {
			Entity entity = TickRequests.DELAYED_TELEPORT_REQUESTS.entrySet().iterator().next().getKey();
			Location location = TickRequests.DELAYED_TELEPORT_REQUESTS.get(entity);
			
			ServerPlayerEntity player = entity.level.getServer().getPlayerList().getPlayer(location.backupUUID);

			if(location.backupUUID != null && player != null) location = PlayerSpawnHelper.getSpawnLocation(location.backupUUID, entity.level);
			
			BlockPos exitPos = location.pos;
			ServerWorld exitDim = location.getDimension(entity.level);
						
			entity.setDeltaMovement(0,0,0);
			
			entity.getServer().getLevel(exitDim.dimension()).getChunk(exitPos);
			
			CompoundNBT compoundtag = new CompoundNBT();
			entity.save(compoundtag);
			compoundtag.putString(CloudyNBTKeys.ID, entity.getType().getRegistryName().toString());

			Entity newEntity = EntityType.loadEntityRecursive(compoundtag, exitDim, (e) -> {
				e.moveTo(exitPos.getX(), exitPos.getY() - 0.5D, exitPos.getZ(), 0, 0);
				return e;
			});
			
			
			entity.remove();
			if (newEntity != null) {
				ExitPortalHelper.summonOrExtendPortal(exitDim, exitPos);
				
				Map<ServerWorld, Integer> map = new HashMap<>();
				map.put(exitDim, (int) (exitDim.getGameTime() + (20 * 3))); // 3 seconds
				TickRequests.DELAYED_TELEPORT_REQUESTS_CONFIRMED.put(newEntity, map);
			}
			
			TickRequests.DELAYED_TELEPORT_REQUESTS.remove(entity);	
		}		
				
		List<Entity> deleteThese = new ArrayList<>();
		
		for(Map.Entry<Entity, Map<ServerWorld, Integer>> entry : TickRequests.DELAYED_TELEPORT_REQUESTS_CONFIRMED.entrySet()) {
		    if(entry.getKey() != null) {
		    	Entity entity = entry.getKey();
				Map<ServerWorld, Integer> map = entry.getValue();		
				ServerWorld world = map.entrySet().iterator().next().getKey();
				int gameTick = map.get(world).intValue();
				if(world.getGameTime() > gameTick) {
					world.addFreshEntity(entity);
					deleteThese.add(entity);
				}
		    }
	    }		
		
		for(Entity key : deleteThese) TickRequests.DELAYED_TELEPORT_REQUESTS_CONFIRMED.remove(key);	
	}
}
