package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

@SuppressWarnings("unused")
public class CloudyBiomes {

	private static RegistryKey<Biome> register(String name) {
		return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(CloudyPorkchops.MODID, name));
	}
	
	public static void init() {};	

}
