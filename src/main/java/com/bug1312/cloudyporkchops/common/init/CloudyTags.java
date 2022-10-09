package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;
import com.bug1312.cloudyporkchops.util.consts.CloudyTagKeys;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("unused")
public class CloudyTags {

	public static class Blocks {

		// Creation Method
		private static TagKey<Block> createTag(String name, Boolean itemTag) {
			if (itemTag) ItemTags.create(new ResourceLocation(CloudyPorkchops.MODID, name));
			return BlockTags.create(new ResourceLocation(CloudyPorkchops.MODID, name));
		}

		private static TagKey<Block> createTag(String name) {
			return createTag(name, true);
		}

	}

	public static class Items {

		public static final TagKey<Item> EXTRA_FOODS = createTag(CloudyTagKeys.GROCERY_FOODS);

		// Creation Method
		private static TagKey<Item> createTag(String name) {
			return ItemTags.create(new ResourceLocation(CloudyPorkchops.MODID, name));
		}
	}

	public static class EntityTypes {

		public static final TagKey<EntityType<?>> CANT_SPRAY = createTag(CloudyTagKeys.ENTITY_CANT_SPRAY);
		public static final TagKey<EntityType<?>> FOODIMAL   = createTag(CloudyTagKeys.ENTITY_FOODIMAL);

		// Creation Method
		private static TagKey<EntityType<?>> createTag(String name) {
			return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(CloudyPorkchops.MODID, name));
		}
	}

	public static class Fluids {

		// Creation Method
		private static TagKey<Fluid> createTag(String name) {
			return FluidTags.create(new ResourceLocation(CloudyPorkchops.MODID, name));
		}
	}

}
