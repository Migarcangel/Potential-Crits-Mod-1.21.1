package com.migar.potentialcrits.event;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.enchantment.crits.CritEffect;
import com.migar.potentialcrits.enchantment.crits.CritRegistry;
import com.migar.potentialcrits.enchantment.crits.CritUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import static com.migar.potentialcrits.event.EventUtils.getEnchantmentLevel;

@EventBusSubscriber(modid = PotentialCrits.MODID)
public class ModEvents {
    public static final ThreadLocal<Boolean> WAS_CRITICAL =  ThreadLocal.withInitial(() -> false);
    public static final ThreadLocal<Boolean> WAS_SUPER_CRIT =  ThreadLocal.withInitial(() -> false);

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        ShieldEvents.onLivingIncomingDamage(event);

        if (!(event.getSource().getEntity() instanceof Player player)) return;

        // Crits will only be applied if damage is superior to 4. This is to avoid click spam.
        if(event.getAmount() < 4) return ;

        ItemStack weapon = player.getMainHandItem();
        int critsApplied = 0;

        // Iterate through ALL registered critical effects
        for (CritEffect critEffect : CritRegistry.getAll()) {
            // Get the level of the specific enchantment on the weapon
            int level = getEnchantmentLevel(weapon, player, critEffect.getId());

            if (level > 0) {
                if(critEffect.applyEffect(player, event, level)) {
                    critsApplied += 1;
                    critEffect.playSpecialEffects(player,event.getEntity());
                }
            }
        }

        if(critsApplied >= 1) {
            CritUtils.playGenericCritParticles(event.getEntity());
            CritUtils.playGenericCritSound(event.getEntity());
        }
        WAS_CRITICAL.set(false);
        WAS_SUPER_CRIT.set(false);
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        WAS_CRITICAL.set(event.isCriticalHit());

        int level = getEnchantmentLevel(player.getMainHandItem(), player, EventUtils.GROUND_CRIT);

        if (level > 0) {
            event.setCriticalHit(false);
        }

        level = getEnchantmentLevel(player.getMainHandItem(), player, EventUtils.SUPER_CRIT);
        float chance = 0.05f * level;

        if (level > 0 && player.level().random.nextFloat() < chance) {
            float multiplier = 0.1f * level;
            event.setDamageMultiplier(1.5f + multiplier);

            if (event.getTarget() instanceof LivingEntity target) {
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,60,0));
            }
            WAS_SUPER_CRIT.set(true);
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
    public static void addCustomTrades(VillagerTradesEvent event) {
        TradingEvents.addCustomTrades(event);
    }

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {BerserkEvents.onEffectExpired(event);}

    // Curación al matar enemigos
    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {BerserkEvents.onKill(event);}
}

