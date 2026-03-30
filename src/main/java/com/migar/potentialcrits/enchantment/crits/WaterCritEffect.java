package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class WaterCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "water_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;

        if(upgradeLevel >= 1){
            chance += 0.05f;
        }

        if (player.isInWaterRainOrBubble() && player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();
            float newDamage = damage + 3;

            if(target.isInWaterRainOrBubble()) {
                newDamage = newDamage + 3;
            }

            float multiplier = 1.0f;
            boolean aquatic = target.getType().is(EntityTypeTags.AQUATIC) || target.getType() == EntityType.DROWNED;

            if(upgradeLevel >= 2 && aquatic) {
                multiplier += 0.10f;
            }

            multiplier = getDepthMultiplier(player, multiplier);

            event.setAmount(newDamage * multiplier);

            player.setAirSupply(player.getAirSupply() + player.getMaxAirSupply()/2);
            return true;
        }
        return false;
    }

    private float getDepthMultiplier(Player player, float multiplier) {
        BlockPos pos = player.blockPosition();
        int depth = 0;
        BlockState block = player.level().getBlockState(pos);

        while(block.is(Blocks.WATER)) {
            pos = pos.above();
            block = player.level().getBlockState(pos);
            depth++;
        }

        if(depth >= 5 && depth < 15) multiplier += 0.05f;
        else if(depth >= 15 && depth < 30) multiplier += 0.10f;
        else if(depth >= 30) multiplier += 0.15f;
        return multiplier;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.SPLASH,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    50,
                    0.6,
                    1,
                    0.6,
                    0.02
            );
            if(target.isUnderWater()) {
                serverLevel.sendParticles(
                        ParticleTypes.BUBBLE,
                        target.getX(),
                        target.getY() + 0.9,
                        target.getZ(),
                        50,
                        0.6,
                        1,
                        0.6,
                        0.02
                );
            }
        }
        player.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.FISHING_BOBBER_SPLASH,
                SoundSource.PLAYERS,
                0.75f,
                1.0f + player.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

