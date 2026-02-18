package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.event.ModEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class TrueCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "true_crit");

    @Override
    public void applyEffect(Player player, LivingDamageEvent.Pre event, int level) {
        LivingEntity target = event.getEntity();

        float chance = level * 0.5f;

        if (ModEvents.WAS_CRITICAL.get() && player.level().random.nextFloat() < chance) {
            float damage = event.getOriginalDamage();

            float trueDamage = damage * 0.2f * level;

            float newHealth = Math.max(target.getHealth() - trueDamage, 0);
            target.setHealth(newHealth);

            PotentialCrits.LOGGER.info(String.valueOf(trueDamage));

            player.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.SHIELD_BREAK,
                    SoundSource.PLAYERS,
                    1.5f,
                    0.5f + player.level().random.nextFloat() * 0.2f
            );

            player.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.PLAYER_ATTACK_CRIT,
                    SoundSource.PLAYERS,
                    0.9f,
                    0.6f
            );

        }

    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

