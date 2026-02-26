package com.migar.potentialcrits.event;

import com.migar.potentialcrits.enchantment.ModEnchantments;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

/**
 * Maneja todos los eventos relacionados con comerciantes y trades
 */
public class TradingEvents {

    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CLERIC) {
            addLightCritTrades(event);
        }
    }

    private static void addLightCritTrades(VillagerTradesEvent event) {
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

        // Light Crit Level 3 (Novice/Master trades)
        trades.get(3).add((entity, randomSource) -> createEnchantedBookOffer(entity, 3, 30, 12));

        // Light Crit Level 4 (Expert/Master trades)
        trades.get(5).add((entity, randomSource) -> createEnchantedBookOffer(entity, 4, 45, 8));
    }

    private static MerchantOffer createEnchantedBookOffer(net.minecraft.world.entity.Entity entity,
                                                          int level, int emeraldCost, int maxUses) {
        var enchantmentRegistry = entity.level().registryAccess()
                .lookup(Registries.ENCHANTMENT).orElseThrow();

        var lightCrit = enchantmentRegistry.get(ModEnchantments.LIGHT_CRIT).orElseThrow();

        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        book.enchant(lightCrit, level);

        return new MerchantOffer(
                new ItemCost(Items.EMERALD, emeraldCost),
                book,
                maxUses,
                getXpReward(level),
                0.05f
        );
    }

    private static int getXpReward(int level) {
        return switch(level) {
            case 3 -> 10;
            case 4 -> 15;
            default -> 5;
        };
    }
}