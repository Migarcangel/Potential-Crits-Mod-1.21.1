package com.migar.potentialcrits.potion;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, PotentialCrits.MODID);

    public static final Holder<Potion> ACCURACY_POTION = POTIONS.register("accuracy_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ACCURACY_EFFECT, 4800, 0)));

    public static final Holder<Potion> ACCURACY_POTION_EXTENDED = POTIONS.register("accuracy_potion_extended",
            () -> new Potion(new MobEffectInstance(ModEffects.ACCURACY_EFFECT, 9600, 0)));

    public static final Holder<Potion> ACCURACY_POTION_STRONG = POTIONS.register("accuracy_potion_strong",
            () -> new Potion(new MobEffectInstance(ModEffects.ACCURACY_EFFECT, 2400, 1)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
