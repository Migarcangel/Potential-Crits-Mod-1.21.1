package com.migar.potentialcrits.enchantment;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.enchantment.effects.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, PotentialCrits.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> FIRE_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("fire_crit", () -> FireCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> THUNDER_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("thunder_crit", () -> ThunderCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> DARK_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("dark_crit", () -> DarkCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> LIGHT_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("light_crit", () -> LightCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> GROUND_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("ground_crit", () -> GroundCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> WATER_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("water_crit", () -> WaterCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> SUPER_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("super_crit", () -> SuperCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> TRUE_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("true_crit", () -> TrueCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> SHIELD_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("shield_crit", () -> ShieldCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> BERSERK_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("berserk_crit", () -> BerserkCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> SMASH_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("smash_crit", () -> SmashCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> UMBRAL_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("umbral_crit", () -> UmbralCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> VAMPIRE_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("vampire_crit", () -> VampireCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> SUNDER_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("sunder_crit", () -> SunderCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> ICE_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("ice_crit", () -> IceCritEnchantmentEffect.CODEC);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> HARVEST_CRIT =
            ENTITY_ENCHANTMENT_EFFECTS.register("harvest_crit", () -> HarvestCritEnchantmentEffect.CODEC);

    public static void register (IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
