package com.bug1312.cloudyporkchops.client.overlay;

import java.util.ArrayList;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Overlay {

	public static ArrayList<Overlay> overlays = new ArrayList<>();

	public Minecraft mc;
	public int width;
	public int height;

	public Overlay() { overlays.add(this); }

	public abstract void render(PoseStack stack, float partialTicks);

	public abstract boolean conditional();

	public void tick() {
		Minecraft minecraft = Minecraft.getInstance();
		Window window = minecraft.getWindow();

		mc = minecraft;
		width = window.getGuiScaledWidth();
		height = window.getGuiScaledHeight();
	}

	protected void addRect(ResourceLocation texture, int x, int y, int width, int height, boolean flipX, boolean flipY) {
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, texture);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(x, 	    y + height, -90).uv(flipX ? 1 : 0, flipY ? 0 : 1).endVertex();
		bufferbuilder.vertex(x + width, y + height, -90).uv(flipX ? 0 : 1, flipY ? 0 : 1).endVertex();
		bufferbuilder.vertex(x + width, y, 		    -90).uv(flipX ? 0 : 1, flipY ? 1 : 0).endVertex();
		bufferbuilder.vertex(x, 	    y, 		    -90).uv(flipX ? 1 : 0, flipY ? 1 : 0).endVertex();
	  	tesselator.end();
	  	RenderSystem.depthMask(true);
	  	RenderSystem.enableDepthTest();
	  	RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
