package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

@SuppressWarnings("unused")
public class CloudySounds {
    
    private static RegistryObject<SoundEvent> register(DeferredRegister<SoundEvent> register, String registryName) {	
        RegistryObject<SoundEvent> SOUND = register.register(registryName,() -> new SoundEvent(new ResourceLocation(CloudyPorkchops.MODID,registryName)));
        return SOUND;
    }

	public static void init() {};	
    
}
