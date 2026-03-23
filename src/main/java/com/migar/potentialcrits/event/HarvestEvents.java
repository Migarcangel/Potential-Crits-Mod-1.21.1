package com.migar.potentialcrits.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

public class HarvestEvents {

    public static void onKill(LivingDeathEvent event) {
        int level = ModEvents.HARVEST_CRIT_LEVEL.get();
        if(level > 0) {
            if(event.getSource().getEntity() instanceof Player player) {
                FoodData hunger = player.getFoodData();
                int food = (int) (0.25 * level);
                hunger.setFoodLevel(hunger.getFoodLevel() + food);

                int duration = 20;

                MobEffectInstance effectInstance = player.getEffect(MobEffects.REGENERATION);
                if(effectInstance != null) {
                    duration = effectInstance.getDuration();
                }

                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration + 10 * level, 0));

                specialEffects(event.getEntity());

            }
        }
    }

    public static void onXpDrop(LivingExperienceDropEvent event) {
        int level = ModEvents.HARVEST_CRIT_LEVEL.get();
        if(level > 0) {
            int droppedXp = event.getDroppedExperience();
            int extraXp = (int) (droppedXp * level * 0.05f);
            int modifiedXp = droppedXp + extraXp;
            event.setDroppedExperience(modifiedXp);
            ModEvents.HARVEST_CRIT_LEVEL.remove();
        }
    }

    private static void specialEffects(LivingEntity target) {
        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.SOUL,
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
                SoundEvents.SOUL_ESCAPE,
                SoundSource.PLAYERS,
                2f,
                1.1f + target.level().random.nextFloat() * 0.2f
        );
    }

}
