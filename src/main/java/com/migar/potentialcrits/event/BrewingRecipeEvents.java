package com.migar.potentialcrits.event;

import com.migar.potentialcrits.item.ModItems;
import com.migar.potentialcrits.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

public class BrewingRecipeEvents {
    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, ModItems.CRIT_GEM.get(), ModPotions.ACCURACY_POTION);
        builder.addMix(ModPotions.ACCURACY_POTION, Items.REDSTONE, ModPotions.ACCURACY_POTION_EXTENDED);
        builder.addMix(ModPotions.ACCURACY_POTION, Items.GLOWSTONE, ModPotions.ACCURACY_POTION_STRONG);
    }
}
