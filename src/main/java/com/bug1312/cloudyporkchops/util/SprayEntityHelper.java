package com.bug1312.cloudyporkchops.util;

import java.io.IOException;
import java.io.InputStream;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.cloudyporkchops.mixins.LivingEntityMixin;
import com.bug1312.cloudyporkchops.util.statics.CloudyNBTKeys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class SprayEntityHelper {
	
	public static ResourceLocation texture = new ResourceLocation(CloudyPorkchops.MODID, "textures/block/elastic_biopolymer_adhesive.png");

	public static boolean isEntitySprayedOn(Entity entity) {
		if (entity != null && entity instanceof LivingEntity) {
//			HelperInterface livingEntity = (HelperInterface) entity;
//			return (livingEntity.isSprayedOn());
			LivingEntity livingEntity = (LivingEntity) entity;
			return (livingEntity.serializeNBT().getInt(CloudyNBTKeys.SPRAYED_ON) > 0);
		}
		return false;
	}
	
	public static ResourceLocation getTexture(ResourceLocation texture) {
		try {
			Minecraft mc = Minecraft.getInstance();
			ResourceLocation newLocation = new ResourceLocation(CloudyPorkchops.MODID, "/textures/generated/" + texture.getPath());
			
			if(mc.getResourceManager().hasResource(newLocation)) return newLocation;
			
			InputStream texStream = mc.getResourceManager().getResource(texture).getInputStream();
			NativeImage inputNative = NativeImage.read(texStream);
			
			InputStream sprayStream = mc.getResourceManager().getResource(SprayEntityHelper.texture).getInputStream();
			NativeImage sprayNative = NativeImage.read(sprayStream);
			
			NativeImage newImageNative = new NativeImage(inputNative.getWidth(), inputNative.getHeight(), false);
			
			for (int y = 0; y < newImageNative.getHeight(); y++) {
				for (int x = 0; x < newImageNative.getWidth(); x++) {
					int color = sprayNative.getPixelRGBA(x % 16, y % 16);
					newImageNative.setPixelRGBA(x, y, color);
				}
			}

			ResourceLocation res = mc.getTextureManager().register(newLocation.getPath(), new DynamicTexture(newImageNative));

			return res;
		} catch (IOException err) { err.printStackTrace(); }
		
		return texture;
	}
}
