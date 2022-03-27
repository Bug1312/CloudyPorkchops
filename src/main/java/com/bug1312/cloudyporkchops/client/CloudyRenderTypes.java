package com.bug1312.cloudyporkchops.client;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class CloudyRenderTypes {
	
	private static String renderTypeName(String name) {
		return CloudyPorkchops.MODID + "_" + name;
	}
		
	public static RenderType deliveratorPortal(ResourceLocation tex, float offsetY) {
		
		RenderType.State rendertype$state = RenderType.State.builder()
			.setTextureState(new RenderState.TextureState(tex, false, false))
			.setTransparencyState(new RenderState.TransparencyState("no_transparency", () -> { RenderSystem.disableBlend(); }, () -> {}))
			.setTexturingState(new RenderState.OffsetTexturingState(0, offsetY))
			.setAlphaState(new RenderState.AlphaState(0.003921569F))
			.setOverlayState(new RenderState.OverlayState(true))
			.createCompositeState(true);
		
		RenderType.State.builder()
				.setTextureState(new RenderState.TextureState(tex, false, false))
				.setTexturingState(new RenderState.OffsetTexturingState(0, offsetY))
				.setWriteMaskState(new RenderState.WriteMaskState(true, false))
				.setFogState(new RenderState.FogState("no_fog", () -> {}, () -> {}))
				.createCompositeState(false);
		
		return RenderType.create(renderTypeName("portal"), DefaultVertexFormats.NEW_ENTITY, 7, 256, true, false, rendertype$state);
	}
	
}
