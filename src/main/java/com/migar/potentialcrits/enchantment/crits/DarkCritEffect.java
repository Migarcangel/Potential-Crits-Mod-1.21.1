package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class DarkCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "dark_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();
            float newDamage;

            boolean undead = target.getType().is(EntityTypeTags.UNDEAD);

            if(!undead) {
                newDamage = damage * 1.25f;
                if(upgradeLevel >= 2) {
                    newDamage *= 1.3f;
                }
            } else {
                newDamage = damage * 0.875f;
                if(upgradeLevel >= 1) {
                    newDamage *= 0.9f;
                }
            }
            event.setAmount(newDamage);

            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS,60,0));
            if(upgradeLevel >= 3) {
                target.addEffect(new MobEffectInstance(MobEffects.WITHER,40,1));
            }
            return true;

        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.WITCH,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    40,
                    0.6,
                    1,
                    0.6,
                    0.02
            );
        }
        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.EVOKER_CAST_SPELL,
                SoundSource.PLAYERS,
                0.7F,
                0.85f + target.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

