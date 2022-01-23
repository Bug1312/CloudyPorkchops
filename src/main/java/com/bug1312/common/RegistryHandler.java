package com.bug1312.common;

import com.bug1312.common.init.CloudyBiomes;
import com.bug1312.common.init.CloudyBlocks;
import com.bug1312.common.init.CloudyEntities;
import com.bug1312.common.init.CloudyItems;
import com.bug1312.common.init.CloudySounds;
import com.bug1312.common.init.CloudyTiles;
import com.bug1312.common.init.CloudyParticles;
import com.bug1312.main.CloudyPorkchops;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CloudyPorkchops.MODID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CloudyPorkchops.MODID);
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CloudyPorkchops.MODID);
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,CloudyPorkchops.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, CloudyPorkchops.MODID);
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CloudyPorkchops.MODID);
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, CloudyPorkchops.MODID);

	public static CloudyItems items;
	public static CloudyBlocks blocks;
	public static CloudyParticles particles;
	public static CloudyBiomes biomes;
	public static CloudyEntities entities;
	public static CloudySounds sounds;
	public static CloudyTiles tiles;

	public static void init() {
		items = new CloudyItems();
		blocks = new CloudyBlocks();
		particles = new CloudyParticles();
		biomes = new CloudyBiomes();
		entities = new CloudyEntities();
		sounds = new CloudySounds();
		tiles = new CloudyTiles();
		
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
		ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
