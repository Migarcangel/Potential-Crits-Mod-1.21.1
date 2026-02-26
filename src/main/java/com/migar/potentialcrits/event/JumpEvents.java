package com.migar.potentialcrits.event;

import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class JumpEvents {

    public static void onLivingJump (LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();

        if (!entity.hasEffect(ModEffects.HEAVY_EFFECT)) return;

        MobEffectInstance effectInstance = entity.getEffect(ModEffects.HEAVY_EFFECT);
        assert effectInstance != null;
        int amplifier = effectInstance.getAmplifier();

        Vec3 v = entity.getDeltaMovement();
        double factor = 0.7 - (amplifier * 0.15);

        entity.setDeltaMovement(v.x, v.y * factor, v.z);
    }
}
