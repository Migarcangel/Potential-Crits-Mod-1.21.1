package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class LightCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "light_crit");

    @Override
    public void applyEffect(Player player, LivingDamageEvent.Pre event, int level) {
        LivingEntity target = event.getEntity();

        float chance = level * 0.05f;

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getNewDamage();
            float newDamage;

            boolean undead = target.getType().is(EntityTypeTags.UNDEAD);

            if(undead) {
                newDamage = damage * 1.25f;
            } else {
                newDamage = damage * 0.90f;
            }
            event.setNewDamage(newDamage);

            target.addEffect(new MobEffectInstance(MobEffects.GLOWING,60,0));

        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

