package com.migar.potentialcrits.event;

import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class BerserkEvents {

    public static void onEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();

        MobEffectInstance instance = event.getEffectInstance();

        if (instance != null && instance.is(ModEffects.BERSERKER_EFFECT)) {

            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            entity.addEffect(new MobEffectInstance(ModEffects.HEAVY_EFFECT, 200, 0));

            if (entity.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.SMOKE,
                        entity.getX(),
                        entity.getY() + 0.8,
                        entity.getZ(),
                        50,
                        0.6,
                        0.5,
                        0.6,
                        0.02
                );
            }
            entity.level().playSound(
                    null,
                    entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.ANVIL_PLACE,
                    SoundSource.PLAYERS,
                    0.8f,
                    0.6f + entity.level().random.nextFloat() * 0.2f
            );
        }
    }

    public static void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity entity) {
            if (entity.hasEffect(ModEffects.BERSERKER_EFFECT)) {
                float missingHealth = entity.getMaxHealth() - entity.getHealth();
                float healAmount = missingHealth * 0.1f;
                entity.heal(healAmount);

                LivingEntity target = event.getEntity();

                if (target.level() instanceof ServerLevel serverLevel) {
                    BlockState bloodBlockState = Blocks.REDSTONE_BLOCK.defaultBlockState();
                    serverLevel.sendParticles(
                            new BlockParticleOption(ParticleTypes.BLOCK, bloodBlockState),
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
                        SoundEvents.WARDEN_ROAR,
                        SoundSource.PLAYERS,
                        0.8f,
                        1.2f + target.level().random.nextFloat() * 0.2f
                );

            }
        }
    }
}
