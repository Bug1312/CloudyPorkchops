package com.bug1312.cloudyporkchops.util.helpers;

import java.io.IOException;
import java.io.InputStream;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.cloudyporkchops.util.mixininterfaces.IMobEntityMixin;
import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class SprayEntityHelper {

	public static ResourceLocation texture = new ResourceLocation(CloudyPorkchops.MODID, "textures/block/elastic_biopolymer_adhesive.png");

	public static boolean isEntitySprayedOn(Entity entity) {
		if (entity != null && entity instanceof Mob) {
			IMobEntityMixin livingEntity = (IMobEntityMixin) entity;
			return (livingEntity.isSprayedOn());
		}
		return false;
	}

	public static ResourceLocation getTexture(ResourceLocation texture) {
		try {
			Minecraft mc = Minecraft.getInstance();
			ResourceLocation newLocation = new ResourceLocation(CloudyPorkchops.MODID, "/textures/generated/" + texture.getPath());

			if (mc.getResourceManager().getResource(newLocation).isPresent()) return newLocation;

			InputStream texStream = mc.getResourceManager().getResource(texture).get().open();
			NativeImage inputNative = NativeImage.read(texStream);

			InputStream sprayStream = mc.getResourceManager().getResource(SprayEntityHelper.texture).get().open();
			NativeImage sprayNative = NativeImage.read(sprayStream);

			NativeImage newImageNative = new NativeImage(inputNative.getWidth(), inputNative.getHeight(), false);

			for (int y = 0; y < newImageNative.getHeight(); y++) {
				for (int x = 0; x < newImageNative.getWidth(); x++) {
					int color = sprayNative.getPixelRGBA(x % 16, y % 16);
					if (inputNative.getLuminanceOrAlpha(x, y) != 0)
						newImageNative.setPixelRGBA(x, y, color);
				}
			}

			ResourceLocation res = mc.getTextureManager().register(newLocation.getPath(), new DynamicTexture(newImageNative));

			return res;
		} catch (IOException err) { err.printStackTrace(); }

		return texture;
	}
}
