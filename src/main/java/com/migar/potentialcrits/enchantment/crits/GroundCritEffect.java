package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class GroundCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "ground_crit");

    @Override
    public void applyEffect(Player player, LivingDamageEvent.Pre event, int level) {
        LivingEntity target = event.getEntity();

        float chance = level * 0.25f;

        if (target.onGround() && player.onGround() && player.level().random.nextFloat() < chance) {
            float damage = event.getNewDamage();
            float newDamage = damage * 1.5f;

            event.setNewDamage(newDamage);

            target.addEffect(new MobEffectInstance(ModEffects.HEAVY_EFFECT ,80,0));
            player.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.ROOTED_DIRT_BREAK,
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

