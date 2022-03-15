package com.bug1312.cloudyporkchops.common.event;

import java.util.Map;

import com.bug1312.cloudyporkchops.common.items.inventions.ShoesCan;
import com.bug1312.cloudyporkchops.util.RaytraceHelper;
import com.bug1312.cloudyporkchops.util.SprayEntityHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
			Entity entity = TickRequests.TELEPORT_REQUESTS.entrySet().iterator().next().getKey();;
			Map<BlockPos, ServerWorld> location = TickRequests.TELEPORT_REQUESTS.get(entity);
			
			BlockPos exitPos = location.entrySet().iterator().next().getKey();
			ServerWorld exitDim = location.get(exitPos);

			entity.setDeltaMovement(0,0,0);
			
			entity.getServer().getLevel(exitDim.dimension()).getChunk(exitPos);
			
			if (exitDim == entity.level) entity.setPos(exitPos.getX(), exitPos.getY(), exitPos.getZ());
			else {
				CompoundNBT compoundtag = new CompoundNBT();
				entity.save(compoundtag);
				compoundtag.putString("id", entity.getType().getRegistryName().toString());

				Entity newEntity = EntityType.loadEntityRecursive(compoundtag, exitDim, (e) -> {
					e.moveTo(exitPos.getX(), exitPos.getY(), exitPos.getZ(), 0, 0);
					return e;
				});

				entity.remove();
				if (newEntity != null) {
					exitDim.addFreshEntity(newEntity);
				}
			}
			
			TickRequests.TELEPORT_REQUESTS.remove(entity);	
		}			
	}
}
