package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.particles.BlockParticleOption;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class VampireCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "vampire_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();
            float newDamage;

            boolean undead = target.getType().is(EntityTypeTags.UNDEAD);

            if(!undead) {
                float health = Math.min(target.getHealth(),30);
                newDamage = damage + health * 0.1f;

                player.heal((health*0.1f)/2);
            } else {
                newDamage = damage;
                player.addEffect(new MobEffectInstance(MobEffects.WITHER,60,1));
            }

            event.setAmount(newDamage);
            return true;

        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {
        boolean undead = target.getType().is(EntityTypeTags.UNDEAD);

        if (target.level() instanceof ServerLevel serverLevel) {
            if(!undead) {
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
            } else {
                serverLevel.sendParticles(
                        ParticleTypes.SMOKE,
                        target.getX(),
                        target.getY() + 0.9,
                        target.getZ(),
                        20,
                        0.6,
                        0.5,
                        0.6,
                        0.02
                );
            }
        }
        if(!undead) {
            target.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.BAT_AMBIENT,
                    SoundSource.PLAYERS,
                    0.7F,
                    0.85f + target.level().random.nextFloat() * 0.2f
            );
        } else {
            target.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.THORNS_HIT,
                    SoundSource.PLAYERS,
                    0.7F,
                    0.85f + target.level().random.nextFloat() * 0.2f
            );
        }

    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

