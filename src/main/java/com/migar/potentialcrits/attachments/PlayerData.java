package com.migar.potentialcrits.attachments;

import net.minecraft.world.entity.player.Player;

public class PlayerData {

    public static int getPermanentChance(Player player) {
        if (player == null) return 0;
        return player.getData(ModAttachments.PERMANENT_CHANCE.get());
    }

    public static void setPermanentUpgrade1(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_1.get())) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_1.get(), true);
        player.setData(ModAttachments.PERMANENT_CHANCE.get(),
                player.getData(ModAttachments.PERMANENT_CHANCE.get()) + 1);
    }

    public static void setPermanentUpgrade2(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_2.get())) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_2.get(), true);
        player.setData(ModAttachments.PERMANENT_CHANCE.get(),
                player.getData(ModAttachments.PERMANENT_CHANCE.get()) + 1);
    }

    public static void setPermanentUpgrade3(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_3.get())) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_3.get(), true);
        player.setData(ModAttachments.PERMANENT_CHANCE.get(),
                player.getData(ModAttachments.PERMANENT_CHANCE.get()) + 1);
    }

    public static void setPermanentUpgrade4(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_4.get())) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_4.get(), true);
        player.setData(ModAttachments.PERMANENT_CHANCE.get(),
                player.getData(ModAttachments.PERMANENT_CHANCE.get()) + 1);
    }

    public static void setPermanentUpgrade5(Player player) {
        if (player == null || player.getData(ModAttachments.PERMANENT_UPGRADE_5.get())) return;

        player.setData(ModAttachments.PERMANENT_UPGRADE_5.get(), true);
        player.setData(ModAttachments.PERMANENT_CHANCE.get(),
                player.getData(ModAttachments.PERMANENT_CHANCE.get()) + 1);
    }
}