package com.bug1312.cloudyporkchops.common.material;

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
		/* Actual Materials */
		SPRAY_ON("elastic_biopolymer_adhesive", 33, new int[] { 0, 0, 0, 3 }, 0, SoundEvents.ARMOR_EQUIP_GOLD, 2.0F, 0.0F, null);

		
		/* Funni code ctrl+c, ctrl+v'd */
		private static final int[] HEALTH_PER_SLOT = new int[] { 13, 15, 16, 11 };
		private final String name;
		private final int durabilityMultiplier;
		private final int[] slotProtections;
		private final int enchantmentValue;
		private final SoundEvent sound;
		private final float toughness;
		private final float knockbackResistance;
		private final LazyValue<Ingredient> repairIngredient;

		private Armor(String _name, int _durabilityMultiplier, int[] _slotProtections, int _enchantmentValue,
				SoundEvent _sound, float _toughness, float _knockbackResistance, Supplier<Ingredient> _repairIngredient) {
			this.name = _name;
			this.durabilityMultiplier = _durabilityMultiplier;
			this.slotProtections = _slotProtections;
			this.enchantmentValue = _enchantmentValue;
			this.sound = _sound;
			this.toughness = _toughness;
			this.knockbackResistance = _knockbackResistance;
			this.repairIngredient = new LazyValue<>(_repairIngredient);
		}

		@OnlyIn(Dist.CLIENT) public String getName() { return CloudyPorkchops.MODID + ":" + name; }

		public int getDurabilityForSlot(EquipmentSlotType slot) { return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMultiplier; }

		public int getDefenseForSlot(EquipmentSlotType slot) { return this.slotProtections[slot.getIndex()]; }

		public int getEnchantmentValue() { return this.enchantmentValue; }

		public SoundEvent getEquipSound() { return this.sound; }

		public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }

		public float getToughness() { return this.toughness; }

		public float getKnockbackResistance() { return this.knockbackResistance; }

	}

}
