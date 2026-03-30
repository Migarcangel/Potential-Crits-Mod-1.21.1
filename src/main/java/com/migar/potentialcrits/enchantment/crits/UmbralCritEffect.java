package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class UmbralCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "umbral_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
        LivingEntity target = event.getEntity();

        chance += level * 0.1f;
        BlockPos pos = target.blockPosition();

        int light = target.level().getMaxLocalRawBrightness(pos);
        float newDamage = 0;

        int blindDuration = 20 * level;
        int inviDuration = 0;

        if(light > 7 ||  target.isOnFire()) {
            return false;
        } else if (light >= 5) {
            newDamage = 2;
            inviDuration = 40;
        } else if (light >= 2) {
            newDamage = 3;
            chance += 0.05f;
            inviDuration = 80;
        } else if (light >= 0) {
            newDamage = 4;
            chance += 0.1f;
            if(upgradeLevel >= 1) {
                blindDuration += 40;
            }
            inviDuration = 160;
        }

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();

            if(upgradeLevel >= 3 && target.getHealth()/target.getMaxHealth() < 0.3f) {
                newDamage *= 2;
            }

            event.setAmount(damage + newDamage);

            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,blindDuration,0));
            if(upgradeLevel >= 2) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,inviDuration,0));
            }
            return true;
        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {
        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.SMOKE,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    40,
                    0.6,
                    0.5,
                    0.6,
                    0.02
            );
        }

        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.APPLY_EFFECT_BAD_OMEN,
                SoundSource.PLAYERS,
                1f,
                1.2f + target.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

