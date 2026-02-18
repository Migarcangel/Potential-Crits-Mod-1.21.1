package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import com.migar.potentialcrits.event.ModEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class ShieldCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "shield_crit");

    @Override
    public void applyEffect(Player player, LivingDamageEvent.Pre event, int level) {
        LivingEntity target = event.getEntity();

        float chance = level * 0.2f;

        if (player.level().random.nextFloat() < chance) {
            float damage = event.getNewDamage() + 3;
            int amplifier = 0;

            ItemStack shield = player.getOffhandItem();

            if (shield.getItem() instanceof net.minecraft.world.item.ShieldItem) {
                damage += 3;
                if (ModEvents.getEnchantmentLevel(shield, player, ID) > 0) {
                    chance = 0.5f;
                    if (player.level().random.nextFloat() < chance) {
                        amplifier = 1;
                    }
                }
            }

            event.setNewDamage(damage);

            player.addEffect(new MobEffectInstance(ModEffects.SHIELD_EFFECT,200,amplifier));

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

