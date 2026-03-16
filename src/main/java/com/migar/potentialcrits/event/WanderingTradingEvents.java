package com.migar.potentialcrits.event;

import com.migar.potentialcrits.enchantment.ModEnchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

public class WanderingTradingEvents {

    /**
     * Adds custom trades to the Wandering Trader.
     * Each critical enchantment gets its own trade with appropriate max level.
     */
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        // Add one trade for each critical enchantment
        for (ResourceKey<Enchantment> enchantmentKey : ModEnchantments.getAllCrits()) {
            rareTrades.add((trader, random) -> {
                var enchantmentRegistry = trader.level().registryAccess()
                        .lookup(Registries.ENCHANTMENT).orElseThrow();
                // Get the enchantment holder from the registry using the key
                var enchantmentHolder = enchantmentRegistry.getOrThrow(enchantmentKey);

                // Get the max level for this enchantment
                int maxLevel = enchantmentHolder.value().getMaxLevel();
                if(enchantmentKey == ModEnchantments.FIRE_CRIT || enchantmentKey == ModEnchantments.THUNDER_CRIT) {
                    maxLevel++;
                }

                // Generate random level between 1 and maxLevel
                int level = 1 + random.nextInt(maxLevel);

                ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                book.enchant(enchantmentHolder, level);

                // Calculate price based on enchantment rarity and level
                int basePrice = getBasePriceForCrit(enchantmentKey);
                int emeraldCost = basePrice + (level * 3) + random.nextInt(8);

                return new MerchantOffer(
                        new ItemCost(Items.EMERALD, emeraldCost),
                        book,
                        2,
                        30,
                        0.05f
                );
            });
        }
    }

    /**
     * Determines the base price in emeralds for a critical enchantment.
     * Prices are based on rarity categories.
     */
    private static int getBasePriceForCrit(ResourceKey<Enchantment> critKey) {
        // Rarest - 40 emeralds
        if (critKey == ModEnchantments.SUPER_CRIT ||
                critKey == ModEnchantments.TRUE_CRIT ||
                critKey == ModEnchantments.SMASH_CRIT) {
            return 40;
        }

        // Medium rare - 30 emeralds
        if (critKey == ModEnchantments.BERSERK_CRIT ||
                critKey == ModEnchantments.SHIELD_CRIT) {
            return 30;
        }

        // Common - 20 emeralds
        if (critKey == ModEnchantments.DARK_CRIT ||
                critKey == ModEnchantments.LIGHT_CRIT ||
                critKey == ModEnchantments.WATER_CRIT ||
                critKey == ModEnchantments.UMBRAL_CRIT ||
                critKey == ModEnchantments.FIRE_CRIT ||
                critKey == ModEnchantments.THUNDER_CRIT ||
                critKey == ModEnchantments.GROUND_CRIT) {
            return 20;
        }
        // Fallback - should never happen if all enchantments are categorized
        return 50;
    }
}