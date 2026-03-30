package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import com.migar.potentialcrits.event.ModEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class TrueCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "true_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;

        if (ModEvents.WAS_VANILLA_CRIT.get() && player.level().random.nextFloat() < chance) {
            float newHealth = getNewHealth(event, level, target,upgradeLevel);

            target.setHealth(newHealth);
            if(upgradeLevel >= 3 && player.level().random.nextFloat() < 0.5f) {
                target.addEffect(new MobEffectInstance(ModEffects.EXPOSED_EFFECT,60,0));
            }
            return true;

        }

        return false;
    }

    private static float getNewHealth(LivingIncomingDamageEvent event, int level, LivingEntity target, int upgradeLevel) {
        float damage = event.getOriginalAmount();

        // True damage
        float multiplierTrueDamage = 0.04f;
        if(upgradeLevel >= 2) {
            multiplierTrueDamage += 0.05f;
        }
        float trueDamage = damage * multiplierTrueDamage * level;

        // Damage depending on health of the target
        float multiplierMaxHealth = 0.005f;
        if(upgradeLevel >= 1) {
            multiplierMaxHealth = 0.01f;
        }
        float maxHealthDamage = target.getMaxHealth() * multiplierMaxHealth;
        maxHealthDamage = Math.min(maxHealthDamage, 3.0f);

        // Combines both damages.
        float totalTrueDamage = trueDamage + maxHealthDamage;

        // Establish the new health and asures it isn't below 0.
        float newHealth = target.getHealth() - totalTrueDamage;
        newHealth = Math.max(newHealth, 0);
        return newHealth;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.CLOUD,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    30,
                    0.6,
                    0.5,
                    0.6,
                    0.02
            );
        }

        target.level().playSound(
                null,
                target.getX(), target.getY(), target.getZ(),
                SoundEvents.ARMOR_EQUIP_CHAIN,
                SoundSource.PLAYERS,
                4f,
                0.8f + target.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

