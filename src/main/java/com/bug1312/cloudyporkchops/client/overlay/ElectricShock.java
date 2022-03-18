package com.bug1312.cloudyporkchops.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class ElectricShock extends Overlay {

	protected static final ResourceLocation PUMPKIN_BLUR_LOCATION = new ResourceLocation("textures/misc/pumpkinblur.png");
	
	@Override
	public void render(MatrixStack stack) {
		mc.textureManager.bind(PUMPKIN_BLUR_LOCATION);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex(0, height, -90).uv(0, 1).endVertex();
		bufferbuilder.vertex(width, height, -90).uv(1, 1).endVertex();
		bufferbuilder.vertex(width, 0, -90).uv(1, 0).endVertex();
		bufferbuilder.vertex(0, 0, -90).uv(0, 0).endVertex();
		tessellator.end();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.enableAlphaTest();
		RenderSystem.color4f(1, 1, 1, 1);
		mc.font.drawShadow(stack, "Test", 4, 4, 0xffffffff);
	}

}
