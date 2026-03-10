package com.migar.potentialcrits.enchantment;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantmentTags {
    public static final TagKey<Enchantment> FIRE_WATER_EXCLUSIVE = create("fire_water_exclusive");
    public static final TagKey<Enchantment> FIRE_UMBRAL_EXCLUSIVE = create("fire_umbral_exclusive");
    public static final TagKey<Enchantment> THUNDER_UMBRAL_EXCLUSIVE = create("thunder_umbral_exclusive");

    public static final TagKey<Enchantment> SHIELD_BERSERK_EXCLUSIVE = create("shield_berserk_exclusive");

    public static final TagKey<Enchantment> DARK_LIGHT_EXCLUSIVE = create("dark_light_exclusive");
    public static final TagKey<Enchantment> LIGHT_UMBRAL_EXCLUSIVE = create("light_umbral_exclusive");

    public static final TagKey<Enchantment> GROUND_SUPER_TRUE_SMASH_EXCLUSIVE = create("ground_super_true_smash_exclusive");
    // These dont work. But i'll leave them in case mod gets changed.
    /*public static final TagKey<Enchantment> GROUND_SUPER_EXCLUSIVE = create("ground_super_exclusive");
    public static final TagKey<Enchantment> GROUND_TRUE_EXCLUSIVE = create("ground_true_exclusive");
    public static final TagKey<Enchantment> GROUND_SMASH_EXCLUSIVE = create("ground_smash_exclusive");
    public static final TagKey<Enchantment> SUPER_SMASH_EXCLUSIVE = create("super_smash_exclusive");
    public static final TagKey<Enchantment> SUPER_TRUE_EXCLUSIVE = create("super_true_exclusive");
    public static final TagKey<Enchantment> SMASH_TRUE_EXCLUSIVE = create("smash_true_exclusive");*/

    private static TagKey<Enchantment> create(String name) {
        return TagKey.create(Registries.ENCHANTMENT,
                ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, name));
    }
}