package com.migar.potentialcrits.item;

import com.migar.potentialcrits.attachments.PermanentUpgrade;
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

public class CritBookItem extends Item {
    public CritBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);

        if (level.isClientSide) {
            // Lado cliente: abrir GUI si no está agachado.
            if (!player.isShiftKeyDown()) {
                ClientHooks.openBook();
            }
        } else {
            // Lado servidor: mostrar estadísticas si está agachado.
            if (player.isShiftKeyDown()) {
                showStadistics(player);
            }
        }

        return InteractionResultHolder.success(itemstack);
    }

    private void showStadistics(Player player) {
        player.displayClientMessage(
                Component.literal("§b§l✦ LIST OF CRITS ✦§r"),
                false
        );

        int totalCrits = PlayerData.getTotalCrits(player);
        player.displayClientMessage(
                Component.literal("§fTotal Crits: " + totalCrits + "/1000§r"),
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

        player.displayClientMessage(
                Component.literal("§a§l✦ PERMANENT UPGRADES ✦§r"),
                false
        );

        for (PermanentUpgrade upgrade : PermanentUpgrade.values()) {
            boolean unlocked = player.getData(upgrade.flag.get());

            Component status = unlocked
                    ? Component.literal("§a✔")
                    : Component.literal("§c✘");

            player.displayClientMessage(
                    Component.literal("§f")
                            .append(Component.literal(upgrade.displayName))
                            .append(Component.literal(": "))
                            .append(status),
                    false
            );
        }

        int permanentChance = PlayerData.getPermanentChance(player);
        player.displayClientMessage(
                Component.literal("§7Permanent Crit Chance: §e" + permanentChance + "%"),
                false
        );
    }
}