package com.bug1312.cloudyporkchops.common.init;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

@SuppressWarnings("unused")
public class CloudyTags {

	public static class Blocks {

		// Creation Method
		private static IOptionalNamedTag<Block> createTag(String name, Boolean itemTag) {
			if (itemTag) ItemTags.createOptional(new ResourceLocation(CloudyPorkchops.MODID, name));
			return BlockTags.createOptional(new ResourceLocation(CloudyPorkchops.MODID, name));
		}

		private static IOptionalNamedTag<Block> createTag(String name) {
			return createTag(name, true);
		}

	}

	public static class Items {

		// Creation Method
		private static IOptionalNamedTag<Item> createTag(String name) {
			return ItemTags.createOptional(new ResourceLocation(CloudyPorkchops.MODID, name));
		}
	}

	public static class EntityTypes {

		// Creation Method
		private static IOptionalNamedTag<EntityType<?>> createTag(String name) {
			return EntityTypeTags.createOptional(new ResourceLocation(CloudyPorkchops.MODID, name));
		}
	}

	public static class Fluids {

		// Creation Method
		private static IOptionalNamedTag<Fluid> createTag(String name) {
			return FluidTags.createOptional(new ResourceLocation(CloudyPorkchops.MODID, name));
		}
	}

}
