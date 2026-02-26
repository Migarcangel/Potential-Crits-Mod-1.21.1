package com.migar.potentialcrits.enchantment.crits;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public interface CritEffect {
    /**
     * Applies the crit
     */
    void applyEffect(Player player, LivingIncomingDamageEvent event, int level);

    /**
     * Unique identifier of the crit
     */
    ResourceLocation getId();
}