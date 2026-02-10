package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class FireCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "fire_crit");

    @Override
    public void applyEffect(Player player, LivingDamageEvent.Pre event, int level) {
        float chance = level * 0.05f;
        LivingEntity target = event.getEntity();

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getNewDamage();
            float fireTicks = target.getRemainingFireTicks();
            if(fireTicks > 0) {
                float newDamage = fireTicks/20.0f;
                target.clearFire();
                event.setNewDamage(damage + newDamage);

                player.level().playSound(
                        null,
                        target.getX(),target.getY(), target.getZ(),
                        SoundEvents.GENERIC_EXTINGUISH_FIRE,
                        SoundSource.NEUTRAL,
                        0.8f,
                        1.0f + player.level().random.nextFloat() * 0.3f
                );
            }
        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

