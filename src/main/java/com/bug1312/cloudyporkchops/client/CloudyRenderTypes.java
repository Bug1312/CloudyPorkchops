package com.bug1312.cloudyporkchops.client;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderStateShard.OffsetTexturingStateShard;
import net.minecraft.client.renderer.RenderStateShard.OverlayStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.TransparencyStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class CloudyRenderTypes {

	private static String renderTypeName(String name) {
		return CloudyPorkchops.MODID + "_" + name;
	}

	public static RenderType deliveratorPortal(ResourceLocation tex, float offsetY) {
		RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
			.setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeEnergySwirlShader))
			.setTextureState(new TextureStateShard(tex, false, false)).setTexturingState(new OffsetTexturingStateShard(0, offsetY))
			.setTransparencyState(new TransparencyStateShard("no_transparency", () -> { RenderSystem.disableBlend(); }, () -> {}))
			.setOverlayState(new OverlayStateShard(true))
			.createCompositeState(false);

		return RenderType.create(renderTypeName("portal"), DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, rendertype$compositestate);
	}

}
