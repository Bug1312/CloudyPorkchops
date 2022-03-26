package com.bug1312.cloudyporkchops.main;

import com.bug1312.cloudyporkchops.client.event.ClientBusEvents;
import com.bug1312.cloudyporkchops.client.event.ClientModEvents;
import com.bug1312.cloudyporkchops.common.event.CommonBusEvents;
import com.bug1312.cloudyporkchops.common.init.RegistryHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CloudyPorkchops.MODID)
public class CloudyPorkchops {
	public static final String MODID = "cloudyporkchops";

	public CloudyPorkchops() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		RegistryHandler.init(modEventBus);
		
		modEventBus.register(ClientModEvents.class);
				
		MinecraftForge.EVENT_BUS.register(ClientBusEvents.class);
		MinecraftForge.EVENT_BUS.register(CommonBusEvents.class);
	
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec, MODID + "-client.toml");		
	}
	
}
