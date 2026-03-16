package com.migar.potentialcrits.event;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

import java.util.Objects;
import java.util.Set;

import static com.migar.potentialcrits.event.EventUtils.getEnchantmentLevel;

public class ShieldEvents {

    private static final ResourceLocation SHIELD_ENCHANTMENT =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "shield_crit");

    private static final Set<ResourceKey<DamageType>> UNBLOCKABLE_DAMAGE_TYPES = Set.of(
            DamageTypes.STARVE, DamageTypes.DROWN,
            DamageTypes.FALL,DamageTypes.IN_WALL,
            DamageTypes.FELL_OUT_OF_WORLD, DamageTypes.GENERIC_KILL,
            DamageTypes.MAGIC, DamageTypes.INDIRECT_MAGIC,
            DamageTypes.WITHER, DamageTypes.DRAGON_BREATH,
            DamageTypes.CRAMMING, DamageTypes.IN_FIRE,
            DamageTypes.ON_FIRE, DamageTypes.LAVA,
            DamageTypes.HOT_FLOOR, DamageTypes.FREEZE,
            DamageTypes.CACTUS, DamageTypes.SWEET_BERRY_BUSH,
            DamageTypes.THORNS, DamageTypes.DRY_OUT
    );

    public static void onBlockedWithShield(LivingShieldBlockEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack shield = player.getOffhandItem();

        int level = getEnchantmentLevel(shield, player, SHIELD_ENCHANTMENT);

        float chance = 0.2f;

        if (level > 0 && player.level().random.nextFloat() < chance) {
            if (!player.hasEffect(ModEffects.SHIELD_EFFECT)) { // Apply the effect
                player.addEffect(new MobEffectInstance(ModEffects.SHIELD_EFFECT,200,0));
            } else { // ALready has the effect. Increase duration and/or amplifier.
                MobEffectInstance effectInstance = player.getEffect(ModEffects.SHIELD_EFFECT);

                assert effectInstance != null;
                int duration = effectInstance.getDuration();
                int amplifier = effectInstance.getAmplifier();

                ItemStack weapon = player.getMainHandItem();

                level = getEnchantmentLevel(weapon, player, SHIELD_ENCHANTMENT);

                chance = 0.5f;

                if(level > 0 && player.level().random.nextFloat() < chance) { // 50% of leveling up.
                    amplifier = 1;
                }
                player.addEffect(new MobEffectInstance(ModEffects.SHIELD_EFFECT,duration + 200,amplifier));
            }
        }
    }

    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();

        if (!target.isBlocking() && target.hasEffect(ModEffects.SHIELD_EFFECT)) {
            var damageType = source.typeHolder().unwrapKey().orElse(null);

            boolean shouldBlock = damageType != null && !UNBLOCKABLE_DAMAGE_TYPES.contains(damageType);

            if (shouldBlock) {
                event.setCanceled(true);

                Holder<MobEffect> shielded = ModEffects.SHIELD_EFFECT;

                int duration = Objects.requireNonNull(target.getEffect(shielded)).getDuration();
                int amplifier = Objects.requireNonNull(target.getEffect(shielded)).getAmplifier();

                target.removeEffect(shielded);

                if (amplifier > 0) {
                    target.addEffect(new MobEffectInstance(ModEffects.SHIELD_EFFECT ,duration,amplifier - 1));
                }

                target.level().playSound(null,
                        target.getX(), target.getY(), target.getZ(),
                        SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS,
                        1.5f, 0.8f + target.level().random.nextFloat() * 0.2f);
            }
        }
    }
}