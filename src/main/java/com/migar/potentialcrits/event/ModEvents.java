package com.migar.potentialcrits.event;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.effect.ModEffects;
import com.migar.potentialcrits.enchantment.crits.CritEffect;
import com.migar.potentialcrits.enchantment.crits.CritRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.Objects;

@EventBusSubscriber(modid = PotentialCrits.MODID)
public class ModEvents {
    public static final ThreadLocal<Boolean> WAS_CRITICAL =  ThreadLocal.withInitial(() -> false);

    @SubscribeEvent
    public static void onLivingDamagePre(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;

        ItemStack weapon = player.getMainHandItem();

        // Iterate through ALL registered critical effects
        for (CritEffect critEffect : CritRegistry.getAll()) {
            // Get the level of the specific enchantment on the weapon
            int level = getEnchantmentLevel(weapon, player, critEffect.getId());

            if (level > 0) {
                critEffect.applyEffect(player, event, level);
            }
        }
        WAS_CRITICAL.set(false);
    }

    public static int getEnchantmentLevel(ItemStack stack, Player player,  ResourceLocation enchantmentId) {
        var holderSet = player.level().registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
        var holder = holderSet.get(ResourceKey.create(Registries.ENCHANTMENT, enchantmentId)).orElse(null); // Get the reference to the enchantment

        assert holder != null;
        return stack.getEnchantmentLevel(holder);
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        WAS_CRITICAL.set(event.isCriticalHit());

        int level = getEnchantmentLevel(player.getMainHandItem(), player,
                ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "ground_crit"));
        if (level > 0) {
            event.setCriticalHit(false);
        }

        float chance = 0.25f;
        level = getEnchantmentLevel(player.getMainHandItem(), player,
                ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "super_crit"));

        if (level > 0 && player.level().random.nextFloat() < chance) {
            event.setDamageMultiplier(2.0f);
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();

        if (!target.isBlocking() && target.hasEffect(ModEffects.SHIELD_EFFECT)) {
            boolean shouldBlock =
                    source.is(DamageTypes.MOB_ATTACK) ||
                            source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.ARROW) ||
                            source.is(DamageTypes.TRIDENT) || source.is(DamageTypes.MOB_PROJECTILE) ||
                            source.is(DamageTypes.EXPLOSION) || source.is(DamageTypes.PLAYER_EXPLOSION) ||
                            source.is(DamageTypes.FIREBALL) || source.is(DamageTypes.UNATTRIBUTED_FIREBALL) ||
                            source.is(DamageTypes.THROWN) || source.is(DamageTypes.FALLING_ANVIL) ||
                            source.is(DamageTypes.STING) || source.is(DamageTypes.WITHER_SKULL) ||
                            source.is(DamageTypes.SONIC_BOOM) || source.is(DamageTypes.FIREWORKS) ||
                            source.is(DamageTypes.LIGHTNING_BOLT) || source.is(DamageTypes.SPIT) ||
                            source.is(DamageTypes.FALLING_BLOCK) || source.is(DamageTypes.FALLING_STALACTITE) ||
                            source.is(DamageTypes.STALAGMITE) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO);

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

    @SubscribeEvent
    public static void onBlockedWithShield(LivingShieldBlockEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack shield = player.getOffhandItem();

        int level = getEnchantmentLevel(shield, player,
                ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "shield_crit"));

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

                level = getEnchantmentLevel(weapon, player,
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "shield_crit"));

                chance = 0.5f;

                if(level > 0 && player.level().random.nextFloat() < chance) { // 50% of leveling up.
                    amplifier = 1;
                }
                player.addEffect(new MobEffectInstance(ModEffects.SHIELD_EFFECT,duration + 200,amplifier));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();

        if (!entity.hasEffect(ModEffects.HEAVY_EFFECT)) return;

        MobEffectInstance effectInstance = entity.getEffect(ModEffects.HEAVY_EFFECT);
        assert effectInstance != null;
        int amplifier = effectInstance.getAmplifier();

        Vec3 v = entity.getDeltaMovement();
        double factor = 0.7 - (amplifier * 0.15);

        entity.setDeltaMovement(v.x, v.y * factor, v.z);
    }
}

