package com.migar.potentialcrits.client.gui;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public class BookRenderer {

    public static final int BOOK_WIDTH = 322;
    public static final int BOOK_HEIGHT = 172;

    private static final ResourceLocation BOOK_TEXTURE = ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "textures/gui/crit_book_template.png");
    private static final int TEXT_LEFT_OFFSET = 24;
    private static final int TEXT_RIGHT_OFFSET = 176;
    private static final int TEXT_TOP_OFFSET = 20;

    private List<BookModels.BookSpread> spreads;
    private Map<String, Integer> sectionSpreadMap;
    private List<BookModels.NavigationButton> navigationButtons;
    private Font font;

    public void setSpreads(List<BookModels.BookSpread> spreads) {
        this.spreads = spreads;
    }

    public void setSectionSpreadMap(Map<String, Integer> sectionSpreadMap) {
        this.sectionSpreadMap = sectionSpreadMap;
    }

    public void setNavigationButtons(List<BookModels.NavigationButton> navigationButtons) {
        this.navigationButtons = navigationButtons;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public List<BookModels.BookSpread> getSpreads() {
        return spreads;
    }

    public Map<String, Integer> getSectionSpreadMap() {
        return sectionSpreadMap;
    }

    public List<BookModels.NavigationButton> getNavigationButtons() {
        return navigationButtons;
    }

    public void render(GuiGraphics guiGraphics, int screenWidth, int screenHeight, int currentSpread, int mouseX, int mouseY) {
        int leftPos = (screenWidth - BOOK_WIDTH) / 2;
        int topPos = (screenHeight - BOOK_HEIGHT) / 2;

        // Dibujar fondo del libro
        guiGraphics.blit(BOOK_TEXTURE, leftPos, topPos, 0, 0, BOOK_WIDTH, BOOK_HEIGHT, BOOK_WIDTH, BOOK_HEIGHT);

        if (spreads != null && currentSpread < spreads.size()) {
            BookModels.BookSpread spread = spreads.get(currentSpread);
            boolean isFirstIntroSpread = currentSpread == sectionSpreadMap.getOrDefault("introduction", -1);

            renderLeftText(guiGraphics, spread.leftLines(), leftPos, topPos);
            if (isFirstIntroSpread) {
                // Spread de Introduction: texto izquierda, botones derecha
                renderNavigationButtons(guiGraphics, mouseX, mouseY);
            } else {
                // Resto de spreads: texto en ambas páginas
                renderRightText(guiGraphics, spread.rightLines(), leftPos, topPos);
            }
        }

        renderPageNumber(guiGraphics, leftPos, topPos, currentSpread);
    }

    private void renderLeftText(GuiGraphics guiGraphics, List<String> lines, int leftPos, int topPos) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int color = (!line.isEmpty() && (line.startsWith("  ") || (i == 0))) ? 0x225522 : 0x334433;
            guiGraphics.drawString(font, line,
                    leftPos + TEXT_LEFT_OFFSET,
                    topPos + TEXT_TOP_OFFSET + (i * font.lineHeight),
                    color, false);
        }
    }

    private void renderRightText(GuiGraphics guiGraphics, List<String> lines, int leftPos, int topPos) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            guiGraphics.drawString(font, line,
                    leftPos + TEXT_RIGHT_OFFSET,
                    topPos + TEXT_TOP_OFFSET + (i * font.lineHeight),
                    0x334433, false);
        }
    }

    private void renderNavigationButtons(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (BookModels.NavigationButton button : navigationButtons) {
            int buttonColor = button.isHovered(mouseX, mouseY) ? 0x88AAAAAA : 0x88666666;
            guiGraphics.fill(button.x, button.y, button.x + button.width, button.y + button.height, buttonColor);
            guiGraphics.renderOutline(button.x, button.y, button.width, button.height, 0xFF334433);

            int textWidth = font.width(button.text);
            int textX = button.x + (button.width - textWidth) / 2;
            int textY = button.y + (button.height - font.lineHeight) / 2;
            guiGraphics.drawString(font, button.text, textX, textY, 0xFFFFFF, false);
        }
    }

    private void renderPageNumber(GuiGraphics guiGraphics, int leftPos, int topPos, int currentSpread) {
        if (spreads != null) {
            String pageNum = (currentSpread + 1) + "/" + spreads.size();
            int pageNumWidth = font.width(pageNum);
            guiGraphics.drawString(font, pageNum,
                    leftPos + BOOK_WIDTH - pageNumWidth - 10,
                    topPos + BOOK_HEIGHT - 15,
                    0x334433, false);
        }
    }
}