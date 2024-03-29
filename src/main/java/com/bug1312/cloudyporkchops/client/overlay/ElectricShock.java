package com.bug1312.cloudyporkchops.client.overlay;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.cloudyporkchops.main.Config;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.resources.ResourceLocation;

public class ElectricShock extends Overlay {

	private static ResourceLocation SHOCK_LOCATION_0 = new ResourceLocation(CloudyPorkchops.MODID, "textures/gui/electric_shock_0.png");
	private static ResourceLocation SHOCK_LOCATION_1 = new ResourceLocation(CloudyPorkchops.MODID, "textures/gui/electric_shock_1.png");
	private static ResourceLocation SHOCK_LOCATION_2 = new ResourceLocation(CloudyPorkchops.MODID, "textures/gui/electric_shock_2.png");
	private static ResourceLocation SHOCK_LOCATION_3 = new ResourceLocation(CloudyPorkchops.MODID, "textures/gui/electric_shock_3.png");

	private ResourceLocation tex0 = getRandomTexture();
	private ResourceLocation tex1 = getRandomTexture();
	private ResourceLocation tex2 = getRandomTexture();
	private ResourceLocation tex3 = getRandomTexture();

	private int tick = 0;
	public double gameTime = 0;

	@Override
	public boolean conditional() {
		return mc.player.level.getGameTime() < gameTime;
	}

	@Override
	public void render(PoseStack stack, float partialTicks) {
		int imageHeight = 32 * 2;
		int imageWidth = 64 * 2;

		addRect(tex0, 0, 0, imageWidth, imageHeight, false, false);
		addRect(tex1, width - imageWidth, 0, imageWidth, imageHeight, true, false);
		addRect(tex2, 0, height - imageHeight, imageWidth, imageHeight, false, true);
		addRect(tex3, width - imageWidth, height - imageHeight, imageWidth, imageHeight, true, true);
	}

	private ResourceLocation getRandomTexture() {
		int texture = (int) (Math.random() * 4);
		switch(texture) {
			case 0: default: return SHOCK_LOCATION_0;
			case 1: 		 return SHOCK_LOCATION_1;
			case 2: 		 return SHOCK_LOCATION_2;
			case 3:			 return SHOCK_LOCATION_3;
		}
	}

	@Override
	public void tick() {
		tick++;
		if (Config.CLIENT.deliverator_flashing.get() && tick % 8 == 0) {
			tex0 = getRandomTexture();
			tex1 = getRandomTexture();
			tex2 = getRandomTexture();
			tex3 = getRandomTexture();
		}
		super.tick();
	}

}
