package com.migar.potentialcrits.event;

import com.migar.potentialcrits.attachments.PlayerData;
import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import static com.migar.potentialcrits.event.EventUtils.BERSERK_CRIT;


public class BerserkEvents {

    public static final String COOLDOWN_TAG = "berserk_crit_cooldown";

    public static void onEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();

        MobEffectInstance instance = event.getEffectInstance();

        if (instance != null && instance.is(ModEffects.BERSERKER_EFFECT)) {
            if (entity instanceof Player player) {
                applyPenalty(player, false);
            }

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

    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player && player.hasEffect(ModEffects.BERSERKER_EFFECT)) {
            applyPenalty(player, true);

            event.setCanceled(true);
            player.setHealth(2); // Survive with 1 heart
            player.level().playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEvents.TOTEM_USE,
                    SoundSource.PLAYERS,
                    0.8f,
                    0.5f + player.level().random.nextFloat() * 0.2f
            );
        }
    }

    private static void applyPenalty(Player player, boolean death) {
        int upgradeLevel = PlayerData.getUpgradeLevel(player, BERSERK_CRIT);

        int effectDuration = 200;
        int cooldownDuration = 600;

        if(death) {
            effectDuration *= 2;
            cooldownDuration *= 6;
        }
        if(upgradeLevel >= 3){
            player.addEffect(new MobEffectInstance(ModEffects.HEAVY_EFFECT, effectDuration/2, 0));
            setCooldown(player, cooldownDuration/2);
        } else {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, effectDuration, 0));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, effectDuration, 0));
            player.addEffect(new MobEffectInstance(ModEffects.HEAVY_EFFECT, effectDuration, 0));

            setCooldown(player, cooldownDuration);
        }
    }

    private static void setCooldown(Player player, int duration) {
        long cooldownEnd = player.level().getGameTime() + duration;
        player.getPersistentData().putLong(COOLDOWN_TAG, cooldownEnd);
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
