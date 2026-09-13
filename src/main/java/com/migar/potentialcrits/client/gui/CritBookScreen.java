package com.migar.potentialcrits.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import static com.migar.potentialcrits.client.gui.BookUtils.playChangePageSound;
import static com.migar.potentialcrits.client.gui.BookUtils.playChangeSectionSound;

public class CritBookScreen extends Screen {

    private final BookContentBuilder contentBuilder = new BookContentBuilder();
    private final BookRenderer renderer = new BookRenderer();

    private final BookModels.BookData bookData;
    private int currentSpread = 0;

    public CritBookScreen(Component title) {
        super(title);
        BookDataLoader dataLoader = new BookDataLoader();
        this.bookData = dataLoader.loadBookData();
    }

    @Override
    protected void init() {
        super.init();
        contentBuilder.initializeSpreads(bookData, this.font);
        contentBuilder.createNavigationButtons(this.width, this.height);
        renderer.setSpreads(contentBuilder.getSpreads());
        renderer.setSectionSpreadMap(contentBuilder.getSectionSpreadMap());
        renderer.setNavigationButtons(contentBuilder.getNavigationButtons());
        renderer.setFont(this.font);
    }

    @Override
    public void render(net.minecraft.client.gui.@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderer.render(guiGraphics, this.width, this.height, currentSpread, mouseX, mouseY);
    }

    @Override
    public void resize(@NotNull Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);
        contentBuilder.createNavigationButtons(this.width, this.height);
        renderer.setNavigationButtons(contentBuilder.getNavigationButtons());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Player player = getMinecraft().player;

        int leftPos = (this.width - BookRenderer.BOOK_WIDTH) / 2;
        int topPos = (this.height - BookRenderer.BOOK_HEIGHT) / 2;

        boolean isFirstIntroSpread = currentSpread == renderer.getSectionSpreadMap().getOrDefault("introduction", -1);

        if (isFirstIntroSpread) {
            for (BookModels.NavigationButton navButton : renderer.getNavigationButtons()) {
                if (navButton.isHovered(mouseX, mouseY)) {
                    Integer targetSpread = renderer.getSectionSpreadMap().get(navButton.targetSectionId);
                    if (targetSpread != null) {
                        currentSpread = targetSpread;
                        if (player != null) {
                            playChangePageSound(player);
                            playChangeSectionSound(player);
                        }
                        return true;
                    }
                }
            }
        }

        // Click izquierdo = página anterior
        if (mouseX >= leftPos && mouseX < leftPos + BookRenderer.BOOK_WIDTH / 2.0 &&
                mouseY >= topPos && mouseY < topPos + BookRenderer.BOOK_HEIGHT) {
            if (currentSpread > 0) {
                currentSpread--;
                if (player != null) {
                    playChangePageSound(player);
                }
                return true;
            }
        }
        // Click derecho = página siguiente
        else if (mouseX >= leftPos + BookRenderer.BOOK_WIDTH / 2.0 && mouseX < leftPos + BookRenderer.BOOK_WIDTH &&
                mouseY >= topPos && mouseY < topPos + BookRenderer.BOOK_HEIGHT) {
            if (currentSpread < renderer.getSpreads().size() - 1) {
                currentSpread++;
                if (player != null) {
                    playChangePageSound(player);
                }
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}