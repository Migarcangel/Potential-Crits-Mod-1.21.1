package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.event.ModEvents;
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

public class SuperCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "super_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
        // This crit is implemented in ModEvents in the event onCriticalHit.
        if(ModEvents.WAS_SUPER_CRIT.get()) {
            LivingEntity target = event.getEntity();

            if (upgradeLevel >= 1) {
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0));
            }

            if(upgradeLevel >= 2) {
                target.setHealth(target.getHealth() - (event.getAmount() * 0.15f));
                event.setAmount(event.getAmount() - (event.getAmount() * 0.15f));
            }
        }
        return ModEvents.WAS_SUPER_CRIT.get();
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.FIREWORK,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    40,
                    0.6,
                    0.4,
                    0.6,
                    0.02
            );
        }
        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.DRAGON_FIREBALL_EXPLODE,
                SoundSource.PLAYERS,
                0.7F,
                1.5f + target.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

