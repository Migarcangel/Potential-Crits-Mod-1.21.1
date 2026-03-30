package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class SmashCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "smash_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {

        chance += level * 0.1f;
        float fallDistance = player.fallDistance;

        chance += Math.min(fallDistance * 0.01f, 0.35f);
        int fallDistanceRequired = 3;
        if(upgradeLevel >= 1) {
            fallDistanceRequired = 2;
        }

        if (fallDistance > fallDistanceRequired && player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();

            float newDamage = getNewDamage(fallDistance,upgradeLevel);

            event.setAmount(damage + newDamage);
            player.fallDistance = 0;
            if(upgradeLevel >= 3) {
                EntityType.WIND_CHARGE.spawn(
                    (ServerLevel) player.level(),
                    player.getOnPos().below(),
                    MobSpawnType.TRIGGERED
                );
            }
            return true;

        }
        return false;
    }

    private static float getNewDamage(float fallDistance, int upgradeLevel) {
        float newDamage = 0;
        float blocks = fallDistance;

        // First 3 blocks deal 4 damage each
        float firstBlocks = Math.min(blocks, 3.0f);
        newDamage += firstBlocks * 4.0f;
        blocks -= firstBlocks;

        // Next 5 blocks deal 2 damage each
        if (blocks > 0) {
            float nextBlocks = Math.min(blocks, 5.0f);
            newDamage += nextBlocks * 2.0f;
            blocks -= nextBlocks;
        }

        // The rest of blocks deal 1 damage each
        if (blocks > 0) {
            newDamage += blocks;
        }

        // Now, we apply the reduction.
        float multiplier = 0.4f;
        if(upgradeLevel >= 2) {
            multiplier = 0.5f;
        }
        newDamage = newDamage * multiplier;
        return newDamage;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.CLOUD,
                    target.getX(),
                    target.getY()+ 0.2,
                    target.getZ(),
                    30,
                    1,
                    0.2,
                    1,
                    0.1
            );
            serverLevel.sendParticles(
                    ParticleTypes.EXPLOSION,
                    target.getX(),
                    target.getY() + 0.2,
                    target.getZ(),
                    8,
                    1,
                    0.2,
                    1,
                    0.05
            );
        }

        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.MACE_SMASH_GROUND,
                SoundSource.PLAYERS,
                4f,
                0.8f + target.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

