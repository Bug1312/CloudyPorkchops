package com.bug1312.cloudyporkchops.common.init;

import net.minecraft.client.particle.EnchantmentTableParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

public class CloudyParticles {

	/* Particles */
	public static RegistryObject<SimpleParticleType> BED = register("bed", true);

	/* Register Methods */
	private static RegistryObject<SimpleParticleType> register(String string, boolean overrideLimiter) {
		return RegistryHandler.PARTICLE_TYPES.register(string, () -> new SimpleParticleType(overrideLimiter));
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.register(BED.get(), EnchantmentTableParticle.NautilusProvider::new);
	}

	public static void init() {};

}
