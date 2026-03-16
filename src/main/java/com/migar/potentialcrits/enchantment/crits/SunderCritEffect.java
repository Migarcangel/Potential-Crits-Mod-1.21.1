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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class SunderCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "sunder_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;
        float damage = event.getAmount();

        if (player.level().random.nextFloat() < chance) {
            float newDamage = damage + 5;

            target.addEffect(new MobEffectInstance(ModEffects.EXPOSED_EFFECT ,80,0));

            event.setAmount(newDamage);
            return true;

        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {
        if (target.level() instanceof ServerLevel serverLevel) {
                BlockState shieldBlockState = Blocks.IRON_BLOCK.defaultBlockState();
                serverLevel.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, shieldBlockState),
                        target.getX(),
                        target.getY() + 0.9,
                        target.getZ(),
                        36,
                        0.8,
                        1,
                        0.8,
                        0.05
                );
        }
        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.MACE_SMASH_AIR,
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

