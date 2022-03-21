package com.bug1312.cloudyporkchops.common.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;

public class CloudyParticles {
	
	/* Particles */
	public static RegistryObject<BasicParticleType> BED = register("bed", true);	
	
	/* Register Methods */
	private static RegistryObject<BasicParticleType> register(String string, boolean overrideLimiter) {
		return RegistryHandler.PARTICLE_TYPES.register(string, () -> new BasicParticleType(overrideLimiter));
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST) 
	public static void registerParticles(ParticleFactoryRegisterEvent event) {
		Minecraft mc = Minecraft.getInstance();
		ParticleManager manager = mc.particleEngine;
		
		manager.register(BED.get(), EnchantmentTableParticle.NautilusFactory::new);
	}

	public static void init() {};	

}
