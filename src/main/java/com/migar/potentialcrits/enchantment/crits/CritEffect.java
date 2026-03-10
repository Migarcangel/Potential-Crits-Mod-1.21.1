package com.migar.potentialcrits.enchantment.crits;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public interface CritEffect {
    /**
     * Applies the crit
     */
    boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level);

    /**
     * Particle and sound specific of the crit
     */
    void playSpecialEffects(Player player, LivingEntity target);

    /**
     * Unique identifier of the crit
     */
    ResourceLocation getId();
}