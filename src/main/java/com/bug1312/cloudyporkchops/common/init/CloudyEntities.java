 package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.common.entity.ExitDeliveratorPortal;
import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.test.AutonEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class CloudyEntities <E extends Entity> {

	/* Entities */
	public static RegistryObject<EntityType<ExitDeliveratorPortal>> EXIT_PORTAL = register("exit_deliverator_portal", EntityType.Builder.of(ExitDeliveratorPortal::new, EntityClassification.AMBIENT).sized(1.0F, 0.0F).build(new ResourceLocation(CloudyPorkchops.MODID, "exit_deliverator_portal").toString()));
	public static RegistryObject<EntityType<AutonEntity>> AUTON_ENTITY = register("auton", EntityType.Builder.of(AutonEntity::new, EntityClassification.AMBIENT).sized(1.0F, 0.0F).build(new ResourceLocation(CloudyPorkchops.MODID, "e").toString()));
	
	/* Register Method */
	private static <E extends Entity> RegistryObject<EntityType<E>> register(String id, EntityType<E> entity) {
		RegistryObject<EntityType<E>> registry = RegistryHandler.ENTITY_TYPES.register(id, () -> entity);	
		return registry;
	}
	
	public static void init() {};	
    
}
