package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
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
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class LightCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "light_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();
            float newDamage;

            boolean undead = target.getType().is(EntityTypeTags.UNDEAD);

            if(undead) {
                newDamage = damage * 1.25f;
            } else {
                newDamage = damage * 0.90f;
            }
            event.setAmount(newDamage);

            target.addEffect(new MobEffectInstance(MobEffects.GLOWING,60,0));
            return true;
        }
        return false;
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {

        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.END_ROD,
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
                SoundEvents.AMETHYST_BLOCK_CHIME,
                SoundSource.PLAYERS,
                2f,
                1.1f + target.level().random.nextFloat() * 0.2f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

