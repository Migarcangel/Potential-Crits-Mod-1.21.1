package com.migar.potentialcrits.enchantment.crits;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public class CritUtils {

    /**
     * Plays the generic crit sound that all crits share
     */
    public static void playGenericCritSound(LivingEntity target) {
        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.PLAYER_ATTACK_CRIT,
                SoundSource.PLAYERS,
                0.75f,
                0.6f
        );
    }

    /**
     * Plays the generic crit particles that all crits share
     */
    public static void playGenericCritParticles(LivingEntity target) {
        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.CRIT,
                    target.getX(),
                    target.getY() + 1.0,
                    target.getZ(),
                    20,
                    1.0,
                    0.8,
                    1.0,
                    0.1
            );
        }
    }
}