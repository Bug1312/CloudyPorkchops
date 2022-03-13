package com.bug1312.cloudyporkchops.common.event;

import com.bug1312.cloudyporkchops.common.items.inventions.ShoesCan;
import com.bug1312.cloudyporkchops.util.RaytraceHelper;
import com.bug1312.cloudyporkchops.util.SprayEntityHelper;

import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.common.ForgeMod;
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
}
