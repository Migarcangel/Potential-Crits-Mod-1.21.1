package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class ThunderCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "thunder_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
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
            // Minimum damage. This is supposed to be unknown
            summonLightning(target, player, 0.1f);

            if(upgradeLevel >= 1 && target.isInWaterOrRain()) {
                target.addEffect(new MobEffectInstance(ModEffects.HEAVY_EFFECT,30,9));
            }

            float damage = event.getAmount();
            float newDamage = 4;
            if(upgradeLevel >= 3) {
                newDamage++;
            }
            event.setAmount(damage + newDamage);

            if(upgradeLevel >= 0 && target.level().isThundering()) {
                summonLightningsInArea(target,player,newDamage);
            }

            return true;

        }
        return false;
    }

    private void summonLightningsInArea(LivingEntity target, Player player,  float newDamage) {
        double radius = 1.0;
        AABB area = target.getBoundingBox().inflate(radius, radius, radius);

        List<LivingEntity> nearbyEntities = target.level().getEntitiesOfClass(
                LivingEntity.class,
                area,
                entity -> entity != target && entity != player && entity.isAlive()
        );

        for (LivingEntity nearbyEntity : nearbyEntities) {
            summonLightning(nearbyEntity,  player,  newDamage);
        }
    }

    private void summonLightning(LivingEntity target, Player player, float newDamage) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.spawn(
                (ServerLevel) player.level(),
                target.getOnPos(),
                MobSpawnType.TRIGGERED
        );

        if (lightning != null) {
            lightning.setDamage(newDamage);
            // Cancels the lightning fire
            lightning.getPersistentData().putBoolean("potentialcrits_no_fire", true);
            lightning.getPersistentData().putBoolean("potentialcrits_ignore_player", true);
        }
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

