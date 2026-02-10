package com.migar.potentialcrits.enchantment;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.enchantment.effects.DarkCritEnchantmentEffect;
import com.migar.potentialcrits.enchantment.effects.FireCritEnchantmentEffect;
import com.migar.potentialcrits.enchantment.effects.LightCritEnchantmentEffect;
import com.migar.potentialcrits.enchantment.effects.ThunderCritEnchantmentEffect;
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

    public static void register (IEventBus eventBus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
