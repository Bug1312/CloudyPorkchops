package com.bug1312.cloudyporkchops.client.overlay;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.cloudyporkchops.main.Config;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class ElectricShock extends Overlay {

	protected static final ResourceLocation SHOCK_LOCATION = new ResourceLocation(CloudyPorkchops.MODID, "textures/gui/electric_shock.png");
	private int tick = 0;
	public double gameTime = 0;
	
	
	@Override
	public boolean conditional() {
		return mc.player.level.getGameTime() < gameTime;
	}
	
	@Override @SuppressWarnings("deprecation")
	public void render(MatrixStack stack, float partialTicks) {
		float newTick = tick / 100F;
		float calc;
		
		if (Config.CLIENT.deliverator_flashing.get())
			// make a cooler wave
			calc = (float) -(-Math.pow(Math.sin(10 * newTick), 10) + ((Math.cos(3 * newTick)) / 5) + 1.6) + 2.5F;
		else calc = 1;
		
		RenderSystem.color3f(calc, calc, calc);

		int imageHeight = 32*2;
		int imageWidth  = 64*2;
		
		addRect(SHOCK_LOCATION, 0, 0, imageWidth, imageHeight, false, false);
		addRect(SHOCK_LOCATION, width - imageWidth, 0, imageWidth, imageHeight, true, false);
		addRect(SHOCK_LOCATION, 0, height - imageHeight, imageWidth, imageHeight, false, true);
		addRect(SHOCK_LOCATION, width - imageWidth, height - imageHeight, imageWidth, imageHeight, true, true);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

	}

	private void addRect(ResourceLocation texture, int x, int y, int width, int height, boolean flipX, boolean flipY) {
		mc.textureManager.bind(texture);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuilder();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.vertex(x, 	    y + height, -90).uv(flipX ? 1 : 0, flipY ? 0 : 1).endVertex();
		bufferbuilder.vertex(x + width, y + height, -90).uv(flipX ? 0 : 1, flipY ? 0 : 1).endVertex();
		bufferbuilder.vertex(x + width, y, 		    -90).uv(flipX ? 0 : 1, flipY ? 1 : 0).endVertex();
		bufferbuilder.vertex(x, 	    y, 		    -90).uv(flipX ? 1 : 0, flipY ? 1 : 0).endVertex();
		tessellator.end();
	}
	
	@Override
	public void tick() {
		if(mc != null && mc.player != null && mc.player.level != null)
		tick = (int) (mc.player.level.getGameTime() - gameTime);
		super.tick();
	}

}
