package com.migar.potentialcrits.event;

import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class BerserkEvents {

    public static void onEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();

        MobEffectInstance instance = event.getEffectInstance();

        if (instance != null && instance.is(ModEffects.BERSERKER_EFFECT)) {

            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            entity.addEffect(new MobEffectInstance(ModEffects.HEAVY_EFFECT, 200, 0));
        }
    }

    public static void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity entity) {
            if (entity.hasEffect(ModEffects.BERSERKER_EFFECT)) {
                float missingHealth = entity.getMaxHealth() - entity.getHealth();
                float healAmount = missingHealth * 0.1f;
                entity.heal(healAmount);
            }
        }
    }
}
