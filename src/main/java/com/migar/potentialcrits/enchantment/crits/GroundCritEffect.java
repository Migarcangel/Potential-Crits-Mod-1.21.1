package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class GroundCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "ground_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
        LivingEntity target = event.getEntity();

        chance += level * 0.25f;

        if (target.onGround() && player.onGround() && player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();

            float newDamage = damage * 1.5f;
            if(upgradeLevel >= 3 && target.hasEffect(ModEffects.HEAVY_EFFECT)) {
                newDamage = damage * 1.75f;
            }

            event.setAmount(newDamage);

            if(upgradeLevel >= 1 && player.level().random.nextFloat() < 0.5f) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE ,60,0));
            }

            target.addEffect(new MobEffectInstance(ModEffects.HEAVY_EFFECT ,80,0));
            if(upgradeLevel >= 2) {
                double radius = 3.0;
                AABB area = target.getBoundingBox().inflate(radius, radius, radius);

                List<LivingEntity> nearbyEntities = target.level().getEntitiesOfClass(
                        LivingEntity.class,
                        area,
                        entity -> entity != target && entity != player && entity.isAlive()
                );

                for (LivingEntity nearbyEntity : nearbyEntities) {
                    nearbyEntity.addEffect(new MobEffectInstance(ModEffects.HEAVY_EFFECT, 80, 0));
                }
            }

            return true;

        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            BlockState shieldBlockState = Blocks.DIRT.defaultBlockState();
            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, shieldBlockState),
                    target.getX(),
                    target.getY() + 0.3,
                    target.getZ(),
                    50,
                    1,
                    0.5,
                    1,
                    0.05
            );
        }
        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.GRAVEL_BREAK,
                SoundSource.PLAYERS,
                4f,
                0.85f + target.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

