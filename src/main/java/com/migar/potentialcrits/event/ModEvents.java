package com.migar.potentialcrits.event;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.attachments.PlayerData;
import com.migar.potentialcrits.enchantment.crits.CritEffect;
import com.migar.potentialcrits.enchantment.crits.CritRegistry;
import com.migar.potentialcrits.enchantment.crits.CritUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import static com.migar.potentialcrits.event.EventUtils.getEnchantmentLevel;
import static com.migar.potentialcrits.event.EventUtils.getInitialChance;

@EventBusSubscriber(modid = PotentialCrits.MODID)
public class ModEvents {
    public static final ThreadLocal<Boolean> WAS_CRIT =  ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> WAS_VANILLA_CRIT =  ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> WAS_SUPER_CRIT =  ThreadLocal.withInitial(() -> false);

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        ShieldEvents.onLivingIncomingDamage(event);

        if (!(event.getSource().getEntity() instanceof Player player)) return;

        // Crits will only be applied if damage is superior to 4. This is to avoid click spam.
        if(event.getAmount() < 4) return ;

        DamageSource damageSource = event.getSource();
        ItemStack weapon = player.getMainHandItem();

        // Verify if damage is from thrown trident.
        boolean isTridentDamage = damageSource.is( DamageTypeTags.IS_PROJECTILE) &&
                damageSource.getDirectEntity() instanceof ThrownTrident;

        if(isTridentDamage) {
            ThrownTrident trident = (ThrownTrident) damageSource.getDirectEntity();
            weapon = trident.getPickupItemStackOrigin();
        }

        int critsApplied = 0;
        float chance = getInitialChance(player);

        // Iterate through ALL registered critical effects
        for (CritEffect critEffect : CritRegistry.getAll()) {
            // Get the level of the specific enchantment on the weapon
            int level = getEnchantmentLevel(weapon, player, critEffect.getId());

            if (level > 0) {
                if(critEffect.applyEffect(player, event, level, chance)) {
                    critsApplied += 1;
                    critEffect.playSpecialEffects(player,event.getEntity());
                }
            }
        }

        if(critsApplied >= 1) {
            CritUtils.playGenericCritParticles(event.getEntity());
            CritUtils.playGenericCritSound(event.getEntity());
        }
        if(critsApplied >= 3) {
            PlayerData.setPermanentUpgrade3(player);
        }
        WAS_VANILLA_CRIT.set(false);
        WAS_SUPER_CRIT.set(false);
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        WAS_VANILLA_CRIT.set(event.isCriticalHit());

        if(event.isCriticalHit()) {
            int level = getEnchantmentLevel(player.getMainHandItem(), player, EventUtils.GROUND_CRIT);

            if (level > 0) {
                event.setCriticalHit(false);
            }

            level = getEnchantmentLevel(player.getMainHandItem(), player, EventUtils.SUPER_CRIT);

            float chance = getInitialChance(player);
            chance += 0.05f * level;

            if (level > 0 && player.level().random.nextFloat() < chance) {
                float multiplier = 0.1f * level;
                event.setDamageMultiplier(1.5f + multiplier);

                if (event.getTarget() instanceof LivingEntity target) {
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,60,0));
                }
                WAS_SUPER_CRIT.set(true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockedWithShield(LivingShieldBlockEvent event) {
        ShieldEvents.onBlockedWithShield(event);
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        JumpEvents.onLivingJump(event);
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {TradingEvents.addCustomTrades(event);}

    @SubscribeEvent
    public static void addCustomWanderingTraderTrades(WandererTradesEvent event) {WanderingTradingEvents.addWanderingTraderTrades(event);}

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {BerserkEvents.onEffectExpired(event);}

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        UpgradesEvents.onKill(event);
        BerserkEvents.onKill(event);
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {BrewingRecipeEvents.onBrewingRecipeRegister(event);}
}

