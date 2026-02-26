package com.migar.potentialcrits.event;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.enchantment.crits.CritEffect;
import com.migar.potentialcrits.enchantment.crits.CritRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import static com.migar.potentialcrits.event.EventUtils.getEnchantmentLevel;

@EventBusSubscriber(modid = PotentialCrits.MODID)
public class ModEvents {
    public static final ThreadLocal<Boolean> WAS_CRITICAL =  ThreadLocal.withInitial(() -> false);

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        ShieldEvents.onLivingIncomingDamage(event);

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

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        WAS_CRITICAL.set(event.isCriticalHit());

        int level = getEnchantmentLevel(player.getMainHandItem(), player, EventUtils.GROUND_CRIT);

        if (level > 0) {
            event.setCriticalHit(false);
        }

        float chance = 0.25f;
        level = getEnchantmentLevel(player.getMainHandItem(), player, EventUtils.SUPER_CRIT);

        if (level > 0 && player.level().random.nextFloat() < chance) {
            event.setDamageMultiplier(2.0f);
            if (event.getTarget() instanceof LivingEntity target) {
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,60,0));
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
    public static void addCustomTrades(VillagerTradesEvent event) {
        TradingEvents.addCustomTrades(event);
    }
}

