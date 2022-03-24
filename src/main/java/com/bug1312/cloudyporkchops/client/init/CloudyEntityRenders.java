package com.bug1312.cloudyporkchops.client.init;

import com.bug1312.cloudyporkchops.client.render.entity.ExitDeliveratorPortalRenderer;
import com.bug1312.cloudyporkchops.common.init.CloudyEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class CloudyEntityRenders {
	
	public static void init() {
		register(CloudyEntities.EXIT_PORTAL.get(), ExitDeliveratorPortalRenderer::new);
	}

	/* Register Method */
	public static <T extends Entity> void register(EntityType<T> entity, IRenderFactory<? super T> renderer) {
		RenderingRegistry.registerEntityRenderingHandler(entity, renderer);
	}
}
