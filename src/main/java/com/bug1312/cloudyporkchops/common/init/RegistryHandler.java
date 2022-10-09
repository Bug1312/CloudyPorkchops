package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CloudyPorkchops.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CloudyPorkchops.MODID);
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CloudyPorkchops.MODID);
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,CloudyPorkchops.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CloudyPorkchops.MODID);
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CloudyPorkchops.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.MENU_TYPES, CloudyPorkchops.MODID);
	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CloudyPorkchops.MODID);

	public static void init(IEventBus eventBus) {
		CloudyBlocks.init();
		CloudyItems.init();
		CloudyParticles.init();
		CloudyBiomes.init();
		CloudyEntities.init();
		CloudySounds.init();
		CloudyContainers.init();
		CloudyTiles.init();

		RegistryHandler.BLOCKS.register(eventBus);
		RegistryHandler.ITEMS.register(eventBus);
		RegistryHandler.PARTICLE_TYPES.register(eventBus);
		RegistryHandler.BIOMES.register(eventBus);
		RegistryHandler.ENTITY_TYPES.register(eventBus);
		RegistryHandler.SOUNDS.register(eventBus);
		RegistryHandler.CONTAINER_TYPE.register(eventBus);
		RegistryHandler.TILE_ENTITY_TYPES.register(eventBus);
	}
}
