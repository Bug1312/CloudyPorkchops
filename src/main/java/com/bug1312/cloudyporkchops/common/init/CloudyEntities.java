 package com.bug1312.cloudyporkchops.common.init;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.common.entity.ExitDeliveratorPortal;
import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.RegistryObject;

public class CloudyEntities {

	/* Entities */
	public static RegistryObject<EntityType<ExitDeliveratorPortal>> EXIT_PORTAL = register("exit_deliverator_portal", () -> EntityType.Builder.of(ExitDeliveratorPortal::new, MobCategory.AMBIENT).sized(1.0F, 0.0F).build(new ResourceLocation(CloudyPorkchops.MODID, "exit_deliverator_portal").toString()));

	/* Register Method */
	private static <E extends Entity> RegistryObject<EntityType<E>> register(String id, Supplier<EntityType<E>> entity) {
		return RegistryHandler.ENTITY_TYPES.register(id, entity);
	}

	public static void init() {};

}
