package com.bug1312.cloudyporkchops.common.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bug1312.cloudyporkchops.common.item.invention.ShoesCan;
import com.bug1312.cloudyporkchops.util.helpers.ExitPortalHelper;
import com.bug1312.cloudyporkchops.util.helpers.PlayerSpawnHelper;
import com.bug1312.cloudyporkchops.util.helpers.PlayerSpawnHelper.Location;
import com.bug1312.cloudyporkchops.util.helpers.RaytraceHelper;
import com.bug1312.cloudyporkchops.util.helpers.SecondsToTickHelper;
import com.bug1312.cloudyporkchops.util.helpers.SprayEntityHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonBusEvents {

	@SubscribeEvent // EntityInteractSpecific is broken with setCanceled
	public static void stopSprayedEntityInteractions(PlayerInteractEvent event) {
		Player player = event.getEntity();
		EntityHitResult entityLookingAt = RaytraceHelper.getHitResult(player, player.getAttributeValue(ForgeMod.REACH_DISTANCE.get()));
		if (!(player.getItemInHand(event.getHand()).getItem() instanceof ShoesCan) && entityLookingAt != null && SprayEntityHelper.isEntitySprayedOn(entityLookingAt.getEntity())) {
			event.setCanceled(true);
		} else if (event.isCancelable()) event.setCanceled(false);
	}

	@SubscribeEvent
	public static <T extends BlockPos> void serverTickEvent(ServerTickEvent event) {
		while(TickRequests.DELAYED_TELEPORT_REQUESTS.size() > 0) {
			Entity entity = TickRequests.DELAYED_TELEPORT_REQUESTS.entrySet().iterator().next().getKey();
			Location location = TickRequests.DELAYED_TELEPORT_REQUESTS.get(entity);

			ServerPlayer player = entity.level.getServer().getPlayerList().getPlayer(location.backupUUID);

			if (location.backupUUID != null && player != null) location = PlayerSpawnHelper.getSpawnLocation(location.backupUUID, entity.level);

			BlockPos exitPos = location.pos;
			ServerLevel exitDim = location.getDimension(entity.level);

			entity.setDeltaMovement(0,0,0);

			entity.getServer().getLevel(exitDim.dimension()).getChunk(exitPos);

			CompoundTag compoundtag = new CompoundTag();
			entity.save(compoundtag);

			Entity newEntity = EntityType.loadEntityRecursive(compoundtag, exitDim, (e) -> {
				e.moveTo(exitPos.getX() + 0.5D, exitPos.getY() - 0.5D, exitPos.getZ() + 0.5D, 0, 0);
				return e;
			});


			entity.remove(RemovalReason.CHANGED_DIMENSION);
			if (newEntity != null) {
				ExitPortalHelper.summonOrExtendPortal(exitDim, exitPos);

				Map<ServerLevel, Integer> map = new HashMap<>();
				map.put(exitDim, (int) (exitDim.getGameTime() + SecondsToTickHelper.toTicks(3)));
				TickRequests.DELAYED_TELEPORT_REQUESTS_CONFIRMED.put(newEntity, map);
			}

			TickRequests.DELAYED_TELEPORT_REQUESTS.remove(entity);
		}

		List<Entity> deleteThese = new ArrayList<>();

		for(Map.Entry<Entity, Map<ServerLevel, Integer>> entry : TickRequests.DELAYED_TELEPORT_REQUESTS_CONFIRMED.entrySet()) {
		    if (entry.getKey() != null) {
		    	Entity entity = entry.getKey();
				Map<ServerLevel, Integer> map = entry.getValue();
				ServerLevel world = map.entrySet().iterator().next().getKey();
				int gameTick = map.get(world).intValue();
				if (world.getGameTime() > gameTick) {
					world.addFreshEntity(entity);
					deleteThese.add(entity);
				}
		    }
	    }

		for(Entity key : deleteThese) TickRequests.DELAYED_TELEPORT_REQUESTS_CONFIRMED.remove(key);
	}
}
