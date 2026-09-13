package com.migar.potentialcrits.client.gui;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class BookUtils {

    public static void playChangePageSound(Player player) {
        player.playNotifySound(
                SoundEvents.BOOK_PAGE_TURN,
                SoundSource.PLAYERS,
                0.75f,
                1.0f + player.level().random.nextFloat() * 0.2f
        );
    }

    public static void playChangeSectionSound(Player player) {
        player.playNotifySound(
                SoundEvents.ENDERMAN_TELEPORT,
                SoundSource.PLAYERS,
                0.75f,
                1.0f + player.level().random.nextFloat() * 0.2f
        );
    }
}
