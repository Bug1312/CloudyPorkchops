package com.bug1312.cloudyporkchops.client.event;

import com.bug1312.cloudyporkchops.client.init.CloudyOverlays;
import com.bug1312.cloudyporkchops.client.init.CloudyTileRenders;
import com.bug1312.cloudyporkchops.client.render.entity.ExitDeliveratorPortalRenderer;
import com.bug1312.cloudyporkchops.common.init.CloudyEntities;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class ClientModEvents {

	@SubscribeEvent
	public static void renderSetup(FMLClientSetupEvent event) {
		CloudyTileRenders.init();
		CloudyOverlays.init();
	}

	@SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(CloudyEntities.EXIT_PORTAL.get(), ExitDeliveratorPortalRenderer::new);
    }
}
