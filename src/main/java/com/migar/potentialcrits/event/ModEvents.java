package com.migar.potentialcrits.event;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.enchantment.crits.CritEffect;
import com.migar.potentialcrits.enchantment.crits.CritRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = PotentialCrits.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
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
    }

    private static int getEnchantmentLevel(ItemStack stack, Player player,  ResourceLocation enchantmentId) {
        var holderSet = player.level().registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();
        var holder = holderSet.get(ResourceKey.create(Registries.ENCHANTMENT, enchantmentId)).orElse(null); // Get the reference to the enchantment

        assert holder != null;
        return stack.getEnchantmentLevel(holder);
    }
}

