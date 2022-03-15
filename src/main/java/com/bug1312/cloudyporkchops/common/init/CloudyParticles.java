package com.bug1312.cloudyporkchops.common.init;

import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.fml.RegistryObject;

public class CloudyParticles {

	@SuppressWarnings("unused")
	private static RegistryObject<BasicParticleType> register(String string, boolean overrideLimiter) {
		return RegistryHandler.PARTICLE_TYPES.register(string, () -> new BasicParticleType(overrideLimiter));
	}
}
