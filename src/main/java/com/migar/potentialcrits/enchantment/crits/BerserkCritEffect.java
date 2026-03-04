package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class BerserkCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "berserk_crit");

    @Override
    public void applyEffect(Player player, LivingIncomingDamageEvent event, int level) {
        LivingEntity target = event.getEntity();

        float chance = level * 0.15f;

        if (player.getHealth()/player.getMaxHealth() <= 0.55 && player.level().random.nextFloat() < chance && !player.hasEffect(MobEffects.WEAKNESS)) {
            float damage = event.getAmount() + 2;

            float newHealth = (1-0.15f) * player.getHealth();
            player.setHealth(newHealth);

            int duration = 200;
            if (player.hasEffect(ModEffects.BERSERKER_EFFECT)) {
                damage += 2;
                MobEffectInstance effectInstance = player.getEffect(ModEffects.BERSERKER_EFFECT);

                assert effectInstance != null;
                duration = effectInstance.getDuration() + 100;

                if(duration > 200) {
                    duration = 200;
                }
            }

            event.setAmount(damage);

            player.addEffect(new MobEffectInstance(ModEffects.BERSERKER_EFFECT,duration,0));

            player.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.ANVIL_PLACE,
                    SoundSource.PLAYERS,
                    1.0f,
                    1.0f + player.level().random.nextFloat() * 0.2f
            );

            player.level().playSound(
                    null,
                    target.getX(), target.getY(), target.getZ(),
                    SoundEvents.PLAYER_ATTACK_CRIT,
                    SoundSource.PLAYERS,
                    0.9f,
                    0.6f
            );

        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

