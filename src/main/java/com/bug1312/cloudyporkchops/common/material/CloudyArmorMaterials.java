package com.bug1312.cloudyporkchops.common.material;

import java.util.function.Supplier;

import com.bug1312.cloudyporkchops.main.CloudyPorkchops;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public class CloudyArmorMaterials {

	public static final Armor SPRAY_ON = new Armor("elastic_biopolymer_adhesive", 33, new int[] { 0, 0, 0, 3 }, 0, SoundEvents.ARMOR_EQUIP_GOLD, 2.0F, 0.0F, null);

	private static record Armor(String name, int durability, int[] protection, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial) implements ArmorMaterial {
	    private static final int[] HEALTH_PER_SLOT = new int[] { 13, 15, 16, 11 };
	    @Override public int getDurabilityForSlot(EquipmentSlot slot) { return HEALTH_PER_SLOT[slot.getIndex()] * this.durability; }
	    @Override public int getDefenseForSlot(EquipmentSlot slot) { return this.protection[slot.getIndex()]; }
	    @Override public int getEnchantmentValue() { return this.enchantability; }
	    @Override public SoundEvent getEquipSound() { return this.equipSound; }
	    @Override public Ingredient getRepairIngredient() { return this.repairMaterial.get(); }
	    @Override public String getName() { return CloudyPorkchops.MODID + ":" + this.name; }
	    @Override public float getToughness() { return this.toughness; }
	    @Override public float getKnockbackResistance() { return this.knockbackResistance; }
	}
}
