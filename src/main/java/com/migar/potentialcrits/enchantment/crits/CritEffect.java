package com.migar.potentialcrits.enchantment.crits;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public interface CritEffect {
    /**
     * Applies the crit
     */
    void applyEffect(Player player, LivingIncomingDamageEvent event, int level);

    /**
     * Plays the generic crit sound that all crits share
     */
    default void playGenericCritSound(Player player, LivingEntity target) {
        player.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.PLAYER_ATTACK_CRIT,
                SoundSource.PLAYERS,
                0.9f,
                0.6f
        );
    }

    default void playGenericCritParticles(Player player, LivingEntity target) {
        for (int i = 0; i < 5; i++) {
            player.level().addParticle(
                    ParticleTypes.CRIT,
                    target.getX() + (player.level().random.nextDouble() - 0.5) * 1.5,
                    target.getY() + 1.0,
                    target.getZ() + (player.level().random.nextDouble() - 0.5) * 1.5,
                    0, 0, 0
            );
        }
    }

    /**
     * Unique identifier of the crit
     */
    ResourceLocation getId();
}