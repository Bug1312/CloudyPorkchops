package com.bug1312.cloudyporkchops.util.statics;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.util.DamageSource;

public class CloudyDamageSources {
	public static DamageSource GROCERY_DELIVERATOR = register("groceryDeliverator");
	
	private static DamageSource register(String key) {
		return new DamageSource(CloudyPorkchops.MODID + "." + key);
	}
}
