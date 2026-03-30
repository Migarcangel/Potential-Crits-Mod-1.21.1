package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class FireCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "fire_crit");

    @Override
    public boolean applyEffect(Player player, LivingIncomingDamageEvent event, int level, float chance, int upgradeLevel) {
        LivingEntity target = event.getEntity();

        chance += level * 0.05f;
        if(upgradeLevel >= 1) {
            chance += 0.05f;
        }

        if (player.level().random.nextFloat() < chance && target.isOnFire()) {
            float damage = event.getAmount();
            float fireTicks = target.getRemainingFireTicks();
            if(fireTicks > 0) {
                float newDamage = fireTicks/20.0f;
                target.clearFire();
                event.setAmount(damage + newDamage);

                if(upgradeLevel >= 3) {
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,100,0));
                }

                if(upgradeLevel >= 2) {
                    upgradeFireAspect(player);
                }

                return true;
            }
        }
        return false;
    }

    private void upgradeFireAspect(Player player) {
        ItemStack weapon = player.getMainHandItem();
        Holder<Enchantment> fireAspect = player.level().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.FIRE_ASPECT);

        if(weapon.getEnchantmentLevel(fireAspect) == 2) {
            weapon.enchant(fireAspect, 3);
            player.level().playSound(
                    null,
                    player.getX(),player.getY(), player.getZ(),
                    SoundEvents.ENCHANTMENT_TABLE_USE,
                    SoundSource.NEUTRAL,
                    1.2f,
                    1.2f + player.level().random.nextFloat() * 0.3f
            );
        }
    }

    @Override
    public void playSpecialEffects(Player player, LivingEntity target) {
        if (target.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.FLAME,
                    target.getX(),
                    target.getY() + 0.9,
                    target.getZ(),
                    28,
                    0.5,
                    1.0,
                    0.5,
                    0.08
            );

            serverLevel.sendParticles(
                    ParticleTypes.LARGE_SMOKE,
                    target.getX(),
                    target.getY() + 0.8,
                    target.getZ(),
                    14,
                    0.6,
                    0.8,
                    0.6,
                    0.02
            );
        }

        target.level().playSound(
                null,
                target.getX(),target.getY(), target.getZ(),
                SoundEvents.GENERIC_EXTINGUISH_FIRE,
                SoundSource.NEUTRAL,
                0.8f,
                1.0f + target.level().random.nextFloat() * 0.3f
        );
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

