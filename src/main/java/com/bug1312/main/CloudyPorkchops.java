package com.bug1312.main;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.CommandDispatcher;
import com.bug1312.common.RegistryHandler;
import com.bug1312.common.event.EventHandlerGeneral;
import com.bug1312.common.init.Biomes;
import com.bug1312.common.init.Blocks;
import com.bug1312.common.init.DMContainer;
import com.bug1312.common.init.Dimensions;
import com.bug1312.common.init.Entities;
import com.bug1312.network.NetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
@Mod(CloudyPorkchops.MODID)
public class CloudyPorkchops {
	public static Random RANDOM = new Random();
	public static CloudyPorkchops INSTANCE;
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "cloudy_porkchops";

	public static Feature<BlockStateFeatureConfig> LAKE, SAND_PATCH;

	public CloudyPorkchops() {
		INSTANCE = this;
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		RegistryHandler.init();

		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::processIMC);

		DMContainer.CONTAINER_TYPE.register(modEventBus);

		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec, "dalekmod-server.toml");

		bothSideSetup(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(EventHandlerGeneral.class);

		IEventBus vengaBus = MinecraftForge.EVENT_BUS;
		vengaBus.addListener(EventPriority.HIGH, this::biomeModification);
	}

	public void biomeModification(final BiomeLoadingEvent event) {
	}

	private void bothSideSetup(IEventBus modEventBus) {
	}

	private void setup(final FMLCommonSetupEvent event) {
		NetworkHandler.register();
	}

	private void processIMC(final InterModProcessEvent event) {
		LOGGER.info("Got IMC {}",
				event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
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
