package com.migar.potentialcrits.item;

import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties CRIT_COOKIE = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(4.8f)
            .alwaysEdible()
            .effect( () -> new MobEffectInstance(ModEffects.ACCURACY_EFFECT,2400,0),0.5f)
            .build();

    public static final FoodProperties CRIT_PIE = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(4.8f)
            .alwaysEdible()
            .effect( () -> new MobEffectInstance(ModEffects.ACCURACY_EFFECT,5020,1),1.0f)
            .effect( () -> new MobEffectInstance(MobEffects.DAMAGE_BOOST,5020,1),1.0f)
            .effect( () -> new MobEffectInstance(ModEffects.EXPOSED_EFFECT,5020,0),1.0f)
            .build();
}
