package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class UmbralCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "umbral_crit");

    @Override
    public void applyEffect(Player player, LivingIncomingDamageEvent event, int level) {
        LivingEntity target = event.getEntity();

        float chance = level * 0.1f;
        BlockPos pos = target.blockPosition();

        int light = target.level().getMaxLocalRawBrightness(pos);
        float newDamage = 0;
        int duration = 20 * level;
        if(light > 7 && !target.isOnFire()) {
            return;
        } else if (light >= 5) {
            newDamage = 2;
        } else if (light >= 2) {
            newDamage = 3;
            chance += 0.05f;
        } else if (light >= 0) {
            newDamage = 4;
            chance += 0.1f;
            duration += 40; // 2 extra seconds of blindness
        }

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();

            event.setAmount(damage + newDamage);

            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,duration,0));

        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

