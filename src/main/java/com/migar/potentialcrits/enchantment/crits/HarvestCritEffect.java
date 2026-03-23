package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.event.ModEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class HarvestCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "harvest_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance) {
        LivingEntity target = event.getEntity();

        chance += 0.33f;

        if (player.level().random.nextFloat() < chance) {
            ModEvents.HARVEST_CRIT_LEVEL.set(level);
            float damage = event.getAmount();
            float newDamage = damage + (level) ; // 1 * level


            event.setAmount(newDamage);

            return true;
        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    25,
                    0.6,
                    1,
                    0.6,
                    0.02
            );
        }
        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.WITHER_SHOOT,
                SoundSource.PLAYERS,
                0.5f,
                1.2f + target.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

