package com.migar.potentialcrits.item;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.block.ModBlocks;
import com.migar.potentialcrits.enchantment.ModEnchantments;
import com.migar.potentialcrits.potion.ModPotions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PotentialCrits.MODID);

    private static void addPotionVariants(CreativeModeTab.Output output, Holder<Potion> potionHolder) {
        output.accept(PotionContents.createItemStack(Items.POTION, potionHolder));
        output.accept(PotionContents.createItemStack(Items.SPLASH_POTION, potionHolder));
        output.accept(PotionContents.createItemStack(Items.LINGERING_POTION, potionHolder));
    }

    public static final Supplier<CreativeModeTab> POTENTIAL_CRITS_TAB = CREATIVE_MODE_TAB.register("potential_crits_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.CRIT_GEM.get()))
                    .title(Component.translatable("creativetab.potential_crits.potential_crits"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Crit Gem
                        output.accept(ModItems.CRIT_GEM);

                        // Crit Ores
                        output.accept(ModBlocks.CRIT_ORE);

                        // All the potions
                        addPotionVariants(output, ModPotions.ACCURACY_POTION);
                        addPotionVariants(output, ModPotions.ACCURACY_POTION_EXTENDED);
                        addPotionVariants(output, ModPotions.ACCURACY_POTION_STRONG);

                        // All the enchantments
                        var enchantments = itemDisplayParameters.holders()
                                .lookupOrThrow(Registries.ENCHANTMENT);

                        for (var key : ModEnchantments.getAllCrits()) {
                            var ench = enchantments.getOrThrow(key);
                            output.accept(EnchantedBookItem.createForEnchantment(
                                    new EnchantmentInstance(ench, ench.value().getMaxLevel())
                            ));
                        }
                    })
                    .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TAB.register(bus);
    }
}