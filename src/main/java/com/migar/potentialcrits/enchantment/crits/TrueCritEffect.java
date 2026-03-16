package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.event.ModEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class TrueCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "true_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;

        if (ModEvents.WAS_VANILLA_CRIT.get() && player.level().random.nextFloat() < chance) {
            float newHealth = getNewHealth(event, level, target);

            target.setHealth(newHealth);
            return true;

        }

        return false;
    }

    private static float getNewHealth(LivingIncomingDamageEvent event, int level, LivingEntity target) {
        float damage = event.getOriginalAmount();

        // True damage
        float trueDamage = damage * 0.04f * level;

        // Damage depending on health of the target
        float maxHealthDamage = target.getMaxHealth() * 0.01f;
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

