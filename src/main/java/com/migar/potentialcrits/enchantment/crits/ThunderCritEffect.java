package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ThunderCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "thunder_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance) {
        LivingEntity target = event.getEntity();

        BlockPos pos = target.blockPosition().above();
        boolean belowSky = target.level().canSeeSky(pos);

        if(!belowSky) { // If target is not below the sky, it won't trigger
            return false;
        }

        boolean thunder = player.level().isThundering();

        float baseChance = 0.1f;
        if (thunder) {
            baseChance *= 2;
        }
        chance += level * baseChance;

        if (player.level().random.nextFloat() < chance) {
            // It may apply additional damage because of the lightning itself. Not consistent though, only sometimes.
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.spawn(
                    (ServerLevel) player.level(),
                    target.getOnPos(),
                    MobSpawnType.TRIGGERED
            );

            if (lightning != null) {
                // Minimum damage. This is supposed to be unknown
                lightning.setDamage(0.1f);
                // Cancels the lightning fire
                lightning.getPersistentData().putBoolean("potentialcrits_no_fire", true);
            }

            float damage = event.getAmount();
            float newDamage = 4;
            event.setAmount(damage + newDamage);

            return true;

        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {
        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.ELECTRIC_SPARK,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    28,
                    0.8,
                    1.5,
                    0.8,
                    0.05
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

