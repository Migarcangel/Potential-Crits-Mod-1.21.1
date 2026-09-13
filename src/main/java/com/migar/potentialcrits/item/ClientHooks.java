package com.migar.potentialcrits.item;

import com.migar.potentialcrits.client.gui.CritBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ClientHooks {
    private ClientHooks() {}

    public static void openBook() {
        Minecraft.getInstance().setScreen(
                new CritBookScreen(Component.translatable("book.potentialcrits.title"))
        );
    }
}