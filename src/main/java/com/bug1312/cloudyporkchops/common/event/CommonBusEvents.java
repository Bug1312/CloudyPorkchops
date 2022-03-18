package com.bug1312.cloudyporkchops.common.event;

import java.util.HashMap;
import java.util.Map;

import com.bug1312.cloudyporkchops.common.items.inventions.ShoesCan;
import com.bug1312.cloudyporkchops.util.PlayerSpawnHelper;
import com.bug1312.cloudyporkchops.util.PlayerSpawnHelper.Location;
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
		while(TickRequests.TELEPORT_REQUESTS.size() > 0) {
			Entity entity = TickRequests.TELEPORT_REQUESTS.entrySet().iterator().next().getKey();
			Location location = TickRequests.TELEPORT_REQUESTS.get(entity);
			
			ServerPlayerEntity player = entity.level.getServer().getPlayerList().getPlayer(location.backupUUID);

			if(location.backupUUID != null && player != null) location = PlayerSpawnHelper.getSpawnLocation(location.backupUUID, entity.level);
			
			BlockPos exitPos = location.pos;
			ServerWorld exitDim = location.dim;
						
			entity.setDeltaMovement(0,0,0);
			
			entity.getServer().getLevel(exitDim.dimension()).getChunk(exitPos);
			
			if (exitDim == entity.level) entity.setPos(exitPos.getX(), exitPos.getY(), exitPos.getZ());
			else {
				CompoundNBT compoundtag = new CompoundNBT();
				entity.save(compoundtag);
				compoundtag.putString("id", entity.getType().getRegistryName().toString());

				Entity newEntity = EntityType.loadEntityRecursive(compoundtag, exitDim, (e) -> {
					e.moveTo(exitPos.getX(), exitPos.getY() - 0.5D, exitPos.getZ(), 0, 0);
					return e;
				});
				
				System.out.println("removing");
				entity.remove();
				if (newEntity != null) {
					// Summon portal first
					exitDim.addFreshEntity(entity);
					// Figure out why wont work, remove above
					
					System.out.println("posting");
					Map<ServerWorld, Integer> map = new HashMap<>();
					map.put(exitDim, (int) (exitDim.getGameTime() + (20 * 3))); // 3 seconds
//					TickRequests.TELEPORT_REQUESTS_CONFIRMED.put(newEntity, map);
				}
			}
			
			TickRequests.TELEPORT_REQUESTS.remove(entity);	
		}			
		
		while(TickRequests.TELEPORT_REQUESTS_CONFIRMED.size() > 0) {
			Entity entity = TickRequests.TELEPORT_REQUESTS_CONFIRMED.entrySet().iterator().next().getKey();
			Map<ServerWorld, Integer> map = TickRequests.TELEPORT_REQUESTS_CONFIRMED.get(entity);		
			ServerWorld world = map.entrySet().iterator().next().getKey();
			int gameTick = map.get(world).intValue();
			
			System.out.println("adding");
			
			if(world.getGameTime() > gameTick) world.addFreshEntity(entity);
			
			TickRequests.TELEPORT_REQUESTS_CONFIRMED.remove(entity);	
		}			
	}
}
