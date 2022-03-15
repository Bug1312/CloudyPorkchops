package com.bug1312.cloudyporkchops.main;

import com.bug1312.cloudyporkchops.client.events.ClientBusEvents;
import com.bug1312.cloudyporkchops.client.events.ClientModEvents;
import com.bug1312.cloudyporkchops.client.init.CloudyTileRenders;
import com.bug1312.cloudyporkchops.client.render.Item3DRendering;
import com.bug1312.cloudyporkchops.common.event.CommonBusEvents;
import com.bug1312.cloudyporkchops.common.init.CloudyContainers;
import com.bug1312.cloudyporkchops.common.init.RegistryHandler;
import com.bug1312.cloudyporkchops.network.NetworkHandler;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
@Mod(CloudyPorkchops.MODID)
public class CloudyPorkchops {
	public static final String MODID = "cloudyporkchops";

	public CloudyPorkchops() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		RegistryHandler.init();

		modEventBus.addListener(this::serverSetup);
		modEventBus.addListener(this::clientSetup);

		CloudyContainers.CONTAINER_TYPE.register(modEventBus);

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec, MODID + "-server.toml");

		bothSideSetup(modEventBus);
		
		MinecraftForge.EVENT_BUS.register(this);
		
		MinecraftForge.EVENT_BUS.register(ClientBusEvents.class);
		MinecraftForge.EVENT_BUS.register(CommonBusEvents.class);
	
		FMLJavaModLoadingContext.get().getModEventBus().register(ClientModEvents.class);
	}


	private void bothSideSetup(IEventBus modEventBus) {
	}

	private void serverSetup(final FMLCommonSetupEvent event) {
		NetworkHandler.register();
	}
	
	private void clientSetup(final FMLClientSetupEvent event) {
		CloudyTileRenders.init();
	}

	@SubscribeEvent
	public void onRegisterCommandEvent(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
	}

	@SubscribeEvent
	public void addReloadListeners(AddReloadListenerEvent event) {
	}
	
	@Mod.EventBusSubscriber(modid = CloudyPorkchops.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ParticleUtil {
		
		@SubscribeEvent
		public static void registerParticles(ParticleFactoryRegisterEvent event) {
		}
	}

}
