package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class CloudySounds {

    private static RegistryObject<SoundEvent> register(String registryName) {
        return RegistryHandler.SOUNDS.register(registryName, () -> new SoundEvent(new ResourceLocation(CloudyPorkchops.MODID,registryName)));
    }

	public static void init() {};

}
