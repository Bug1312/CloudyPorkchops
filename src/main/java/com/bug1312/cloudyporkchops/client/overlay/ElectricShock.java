package com.bug1312.cloudyporkchops.client.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ElectricShock extends Overlay {

	protected static final ResourceLocation PUMPKIN_BLUR_LOCATION = new ResourceLocation("textures/misc/nausea.png");
	private int tick = 0;
	
	
	@Override @SuppressWarnings("deprecation")
	public void render(MatrixStack stack, float partialTicks) {
		// Add config to not have flashing
		float calc = (MathHelper.sin(tick * 5 + (7 * (float) Math.PI / 2)) + 10 - 1) / 10;
		RenderSystem.color4f(calc, calc, calc, 1);

		addSquare(PUMPKIN_BLUR_LOCATION, 0, 0, 80);
		addSquare(PUMPKIN_BLUR_LOCATION, width - 80, 0, 80);
		addSquare(PUMPKIN_BLUR_LOCATION, 0, height - 80, 80);
		addSquare(PUMPKIN_BLUR_LOCATION, width - 80, height - 80, 80);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

	}

	private void addSquare(ResourceLocation texture, int x, int y, int size) {
		mc.textureManager.bind(texture);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex(x, y + size, -90).uv(0, 1).endVertex();
		bufferbuilder.vertex(x + size, y + size, -90).uv(1, 1).endVertex();
		bufferbuilder.vertex(x + size, y, -90).uv(1, 0).endVertex();
		bufferbuilder.vertex(x, y, -90).uv(0, 0).endVertex();
		tessellator.end();
	}
	
	@Override
	public void tick() {
		tick++;
		super.tick();
	}

}
