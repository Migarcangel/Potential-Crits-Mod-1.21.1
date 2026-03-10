package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class FireCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "fire_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level) {
        float chance = level * 0.05f;
        LivingEntity target = event.getEntity();

        if (player.level().random.nextFloat() < chance && target.isOnFire()) {
            float damage = event.getAmount();
            float fireTicks = target.getRemainingFireTicks();
            if(fireTicks > 0) {
                float newDamage = fireTicks/20.0f;
                target.clearFire();
                event.setAmount(damage + newDamage);

                return true;
            }
        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {
        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    28,
                    0.5,
                    1.0,
                    0.5,
                    0.08
            );

            serverLevel.sendParticles(
                    ParticleTypes.LARGE_SMOKE,
                    target.getX(),
                    target.getY() + 0.8,
                    target.getZ(),
                    14,
                    0.6,
                    0.8,
                    0.6,
                    0.02
            );
        }

        target.level().playSound(
                null,
                target.getX(),target.getY(), target.getZ(),
                SoundEvents.GENERIC_EXTINGUISH_FIRE,
                SoundSource.NEUTRAL,
                0.8f,
                1.0f + target.level().random.nextFloat() * 0.3f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

