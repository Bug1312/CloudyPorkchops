package com.bug1312.main;

import java.util.Random;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bug1312.client.init.Item3DRegister;
import com.bug1312.common.RegistryHandler;
import com.bug1312.common.event.EventHandlerGeneral;
import com.bug1312.common.init.CloudyContainers;
import com.bug1312.common.items.Item3D;
import com.bug1312.network.NetworkHandler;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
		MinecraftForge.EVENT_BUS.register(EventHandlerGeneral.class);
	}


	private void bothSideSetup(IEventBus modEventBus) {
	}

	private void serverSetup(final FMLCommonSetupEvent event) {
		NetworkHandler.register();
	}
	
	private void clientSetup(final FMLClientSetupEvent event) {
		Item3DRegister.registerItems();
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
