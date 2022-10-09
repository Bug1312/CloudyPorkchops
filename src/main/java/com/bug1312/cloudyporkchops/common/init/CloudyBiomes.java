package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class CloudyBiomes {

	private static RegistryObject<Biome> register(String name) {
		return RegistryObject.create(new ResourceLocation(CloudyPorkchops.MODID, name), ForgeRegistries.BIOMES);
	}

	public static void init() {};

}
