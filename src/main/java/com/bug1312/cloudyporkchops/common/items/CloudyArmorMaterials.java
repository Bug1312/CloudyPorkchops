package com.bug1312.cloudyporkchops.common.items;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CloudyArmorMaterials {

	public enum Armor implements IArmorMaterial {
		SPRAY_ON("elastic_biopolymer_adhesive", 33, new int[] { 0, 0, 0, 3 }, 10, SoundEvents.ARMOR_EQUIP_GOLD, 2.0F,
				0.0F, null);

		private static final int[] HEALTH_PER_SLOT = new int[] { 13, 15, 16, 11 };
		private final String name;
		private final int durabilityMultiplier;
		private final int[] slotProtections;
		private final int enchantmentValue;
		private final SoundEvent sound;
		private final float toughness;
		private final float knockbackResistance;
		private final LazyValue<Ingredient> repairIngredient;

		private Armor(String p_i231593_3_, int p_i231593_4_, int[] p_i231593_5_, int p_i231593_6_,
				SoundEvent p_i231593_7_, float p_i231593_8_, float p_i231593_9_, Supplier<Ingredient> p_i231593_10_) {
			this.name = p_i231593_3_;
			this.durabilityMultiplier = p_i231593_4_;
			this.slotProtections = p_i231593_5_;
			this.enchantmentValue = p_i231593_6_;
			this.sound = p_i231593_7_;
			this.toughness = p_i231593_8_;
			this.knockbackResistance = p_i231593_9_;
			this.repairIngredient = new LazyValue<>(p_i231593_10_);
		}

		public int getDurabilityForSlot(EquipmentSlotType p_200896_1_) {
			return HEALTH_PER_SLOT[p_200896_1_.getIndex()] * this.durabilityMultiplier;
		}

		public int getDefenseForSlot(EquipmentSlotType p_200902_1_) {
			return this.slotProtections[p_200902_1_.getIndex()];
		}

		public int getEnchantmentValue() {
			return this.enchantmentValue;
		}

		public SoundEvent getEquipSound() {
			return this.sound;
		}

		public Ingredient getRepairIngredient() {
			return this.repairIngredient.get();
		}

		@OnlyIn(Dist.CLIENT)
		public String getName() {
			return CloudyPorkchops.MODID + ":" + name;
		}

		public float getToughness() {
			return this.toughness;
		}

		public float getKnockbackResistance() {
			return this.knockbackResistance;
		}

	}

}
