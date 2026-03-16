package com.migar.potentialcrits.attachments;

import net.minecraft.world.entity.player.Player;

public class PlayerData {

    public static int getPermanentChance(Player player) {
        if (player == null) return 0;
        return player.getData(ModAttachments.PERMANENT_CHANCE);
    }

    // UPGRADE 1

    public static void setPermanentUpgrade1(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_1)) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_1, true);
        player.setData(ModAttachments.PERMANENT_CHANCE,
                player.getData(ModAttachments.PERMANENT_CHANCE) + 1);
    }

    // UPGRADE 2

    public static void setPermanentUpgrade2(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_2)) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_2, true);
        player.setData(ModAttachments.PERMANENT_CHANCE,
                player.getData(ModAttachments.PERMANENT_CHANCE) + 1);
    }

    // UPGRADE 3

    public static void setPermanentUpgrade3(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_3)) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_3, true);
        player.setData(ModAttachments.PERMANENT_CHANCE,
                player.getData(ModAttachments.PERMANENT_CHANCE) + 1);
    }

    // UPGRADE 4

    public static void setPermanentUpgrade4(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_4)) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_4, true);
        player.setData(ModAttachments.PERMANENT_CHANCE,
                player.getData(ModAttachments.PERMANENT_CHANCE) + 1);
    }

    // UPGRADE 5

    public static void setPermanentUpgrade5(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_5)) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_5, true);
        player.setData(ModAttachments.PERMANENT_CHANCE,
                player.getData(ModAttachments.PERMANENT_CHANCE) + 1);
    }
}