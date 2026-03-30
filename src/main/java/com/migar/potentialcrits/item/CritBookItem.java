package com.migar.potentialcrits.item;

import com.migar.potentialcrits.attachments.PlayerData;
import com.migar.potentialcrits.enchantment.crits.CritEffect;
import com.migar.potentialcrits.enchantment.crits.CritRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.migar.potentialcrits.event.EventUtils.getEnchantmentLevel;

public class CritBookItem extends Item {
    public CritBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(
                        "§b§l✦ LIST OF CRITS ✦§r"
                ),
                false
        );
        int totalCrits = PlayerData.getTotalCrits(player);
        player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(
                        "§fTotal Crits: " + totalCrits + "§r"
                ),
                false
        );

        for (CritEffect critEffect : CritRegistry.getAll()) {
            int critsCount = PlayerData.getCritCount(player, critEffect.getId());

            String critKey = critEffect.getId().getPath();

            Component critName = Component.translatable("enchantment.potentialcrits." + critKey);

            player.displayClientMessage(
                    Component.literal("§f")
                            .append(critName)
                            .append(Component.literal(": §e" + critsCount + "§r")),
                    false
            );
        }

        return InteractionResultHolder.success(itemstack);
    }
}
