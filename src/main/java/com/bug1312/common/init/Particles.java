package com.bug1312.common.init;

import com.bug1312.common.RegistryHandler;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.fml.RegistryObject;

public class Particles {

	@SuppressWarnings("unused")
	private static RegistryObject<BasicParticleType> register(String string, boolean overrideLimiter) {
		return RegistryHandler.PARTICLE_TYPES.register(string, () -> new BasicParticleType(overrideLimiter));
	}
}
