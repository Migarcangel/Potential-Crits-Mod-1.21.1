package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.event.ModEvents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class IceCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "ice_crit");

    private static final TagKey<Biome> COLD_BIOMES = TagKey.create(
            Registries.BIOME,
            ResourceLocation.parse("c:is_cold")
    );

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;

        boolean frozen = target.getTicksFrozen() > 140;
        ModEvents.WAS_FROZEN.set(frozen);
        if(frozen) {
            chance *= 2;
        }

        boolean inColdBiome = target.level().getBiome(target.blockPosition())
                .is(COLD_BIOMES);

        if ((inColdBiome || frozen) && player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();
            float newDamage;

            if(frozen) {
                newDamage = damage + 5;
                boolean shouldBreakFreeze = upgradeLevel < 2 || !(player.level().random.nextFloat() < 0.5f);

                if(shouldBreakFreeze) {
                    target.setTicksFrozen(0);
                }

                if(upgradeLevel >= 3) {
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200, 0));
                }

            } else {
                newDamage = damage + 2;
                target.setTicksFrozen(300);
                if(upgradeLevel >= 1) {
                    target.setTicksFrozen(380);
                }
            }

            event.setAmount(newDamage);
            return true;
        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {
        boolean frozen = ModEvents.WAS_FROZEN.get();

        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.SNOWFLAKE,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    50,
                    0.8,
                    1,
                    0.8,
                    0.02
            );
            if(frozen) {
                BlockState shieldBlockState = Blocks.ICE.defaultBlockState();
                serverLevel.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, shieldBlockState),
                        target.getX(),
                        target.getY() + 0.9,
                        target.getZ(),
                        36,
                        0.6,
                        1,
                        0.6,
                        0.05
                );
            }
        }
        if(!frozen) {
            player.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.POWDER_SNOW_PLACE,
                    SoundSource.PLAYERS,
                    0.75f,
                    1.0f + player.level().random.nextFloat() * 0.2f
            );
        } else {
            player.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.GLASS_BREAK,
                    SoundSource.PLAYERS,
                    0.75f,
                    0.7f + player.level().random.nextFloat() * 0.2f
            );
        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

