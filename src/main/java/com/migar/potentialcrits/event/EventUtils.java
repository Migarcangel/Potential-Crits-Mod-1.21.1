package com.migar.potentialcrits.event;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Utilidades compartidas para el manejo de críticos
 */
public class EventUtils {

    public static final ResourceLocation GROUND_CRIT =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "ground_crit");

    public static final ResourceLocation SUPER_CRIT =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "super_crit");

    public static int getEnchantmentLevel(ItemStack stack, Player player,  ResourceLocation enchantmentId) {
        var holderSet = player.level().registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
        var holder = holderSet.get(ResourceKey.create(Registries.ENCHANTMENT, enchantmentId)).orElse(null); // Get the reference to the enchantment

        assert holder != null;
        return stack.getEnchantmentLevel(holder);
    }
}