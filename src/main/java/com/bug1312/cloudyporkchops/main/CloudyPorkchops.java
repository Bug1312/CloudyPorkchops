package com.bug1312.cloudyporkchops.main;

import com.bug1312.cloudyporkchops.client.event.ClientBusEvents;
import com.bug1312.cloudyporkchops.client.event.ClientModEvents;
import com.bug1312.cloudyporkchops.client.init.CloudyEntityRenders;
import com.bug1312.cloudyporkchops.client.init.CloudyOverlays;
import com.bug1312.cloudyporkchops.client.init.CloudyTileRenders;
import com.bug1312.cloudyporkchops.common.event.CommonBusEvents;
import com.bug1312.cloudyporkchops.common.init.CloudyContainers;
import com.bug1312.cloudyporkchops.common.init.CloudyEntities;
import com.bug1312.cloudyporkchops.common.init.RegistryHandler;
import com.bug1312.cloudyporkchops.network.NetworkHandler;
import com.bug1312.cloudyporkchops.test.ExampleEntity;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("unused")
@Mod(CloudyPorkchops.MODID)
public class CloudyPorkchops {
	public static final String MODID = "cloudyporkchops";

	public CloudyPorkchops() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		RegistryHandler.init(modEventBus);

		modEventBus.addListener(this::serverSetup);
		modEventBus.addListener(this::clientSetup);

		CloudyContainers.CONTAINER_TYPE.register(modEventBus);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec, MODID + "-client.toml");

		bothSideSetup(modEventBus);
		
		MinecraftForge.EVENT_BUS.register(this);
		
		MinecraftForge.EVENT_BUS.register(ClientBusEvents.class);
		MinecraftForge.EVENT_BUS.register(CommonBusEvents.class);
	
		modEventBus.register(ClientModEvents.class);
	}


	private void bothSideSetup(IEventBus modEventBus) {
	}

	@SuppressWarnings("deprecation")
	private void serverSetup(final FMLCommonSetupEvent event) {
		NetworkHandler.register();
		
		DeferredWorkQueue.runLater(() -> {
			GlobalEntityTypeAttributes.put(CloudyEntities.TEST.get(), ExampleEntity.setAttributes().build());
		});
	}
	
	private void clientSetup(final FMLClientSetupEvent event) {
		CloudyTileRenders.init();
		CloudyEntityRenders.init();
		CloudyOverlays.init(); 
	}

	@SubscribeEvent
	public void onRegisterCommandEvent(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
	}

	@SubscribeEvent
	public void addReloadListeners(AddReloadListenerEvent event) {
	}

}
