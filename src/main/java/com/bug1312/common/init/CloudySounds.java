package com.bug1312.common.init;

import com.bug1312.main.CloudyPorkchops;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class CloudySounds {
    
    public static RegistryObject<SoundEvent> buildSound(DeferredRegister<SoundEvent> register, String registryName) {	
        RegistryObject<SoundEvent> SOUND = register.register(registryName,() -> new SoundEvent(new ResourceLocation(CloudyPorkchops.MODID,registryName)));
        return SOUND;
    }
    
}
