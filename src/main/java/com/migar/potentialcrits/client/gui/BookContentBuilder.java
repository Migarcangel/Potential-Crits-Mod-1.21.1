package com.migar.potentialcrits.client.gui;

import net.minecraft.client.gui.Font;
import java.util.*;

public class BookContentBuilder {

    private static final int BOOK_WIDTH = 322;
    private static final int BOOK_HEIGHT = 172;
    private static final int TEXT_WIDTH = 128;
    private static final int LINES_PER_PAGE = 28; // 14 por cada cara

    private final List<BookModels.BookSpread> spreads = new ArrayList<>();
    private final Map<String, Integer> sectionSpreadMap = new HashMap<>();
    private final List<BookModels.NavigationButton> navigationButtons = new ArrayList<>();

    public void initializeSpreads(BookModels.BookData bookData, Font font) {
        spreads.clear();
        sectionSpreadMap.clear();

        for (BookModels.BookCategory category : bookData.categories) {
            sectionSpreadMap.put(category.title.toLowerCase().replace(" ", "_"), spreads.size());
            addCategoryToSpreads(category, 0, font);
        }
    }

    private void addCategoryToSpreads(BookModels.BookCategory category, int indentLevel, Font font) {
        if (category == null) return;

        if (category.content != null && !category.content.isEmpty()) {
            List<String> allLines = new ArrayList<>();

            String titlePrefix = indentLevel > 0 ? "  " : "";
            allLines.add(titlePrefix + category.title);
            allLines.add("");

            List<String> contentLines = wrapText(category.content, font);
            allLines.addAll(contentLines);

            int totalSpreads = (int) Math.ceil((double) allLines.size() / LINES_PER_PAGE);

            for (int i = 0; i < totalSpreads; i++) {
                int start = i * LINES_PER_PAGE;
                int end = Math.min(start + LINES_PER_PAGE, allLines.size());
                List<String> spreadLines = allLines.subList(start, end);

                int halfLines = LINES_PER_PAGE / 2;
                List<String> leftLines = spreadLines.subList(0, Math.min(halfLines, spreadLines.size()));
                List<String> rightLines = spreadLines.size() > halfLines ?
                        spreadLines.subList(halfLines, spreadLines.size()) : new ArrayList<>();

                spreads.add(new BookModels.BookSpread(category.title, leftLines, rightLines));
            }
        }

        if (category.subcategories != null) {
            for (BookModels.BookCategory subcategory : category.subcategories) {
                addCategoryToSpreads(subcategory, indentLevel + 1, font);
            }
        }
    }

    private List<String> wrapText(String text, Font font) {
        List<String> lines = new ArrayList<>();

        for (String paragraph : text.split("\n")) {
            if (paragraph.isEmpty()) {
                lines.add("");
                continue;
            }

            String[] words = paragraph.split(" ");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words) {
                if (font.width(currentLine + " " + word) <= BookContentBuilder.TEXT_WIDTH) {
                    if (!currentLine.isEmpty()) currentLine.append(" ");
                    currentLine.append(word);
                } else {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                }
            }
            if (!currentLine.isEmpty()) lines.add(currentLine.toString());
        }

        return lines;
    }

    public void createNavigationButtons(int width, int height) {
        navigationButtons.clear();

        int leftPos = (width - BOOK_WIDTH) / 2;
        int topPos = (height - BOOK_HEIGHT) / 2;

        int buttonStartX = leftPos + BOOK_WIDTH / 2 + 20;
        int buttonStartY = topPos + 40;
        int buttonWidth = 100;
        int buttonHeight = 20;
        int buttonSpacing = 10;

        navigationButtons.add(new BookModels.NavigationButton(
                buttonStartX, buttonStartY, buttonWidth, buttonHeight,
                "General Info", "general_info"
        ));

        navigationButtons.add(new BookModels.NavigationButton(
                buttonStartX, buttonStartY + buttonHeight + buttonSpacing, buttonWidth, buttonHeight,
                "List of Crits", "list_of_crits"
        ));

        navigationButtons.add(new BookModels.NavigationButton(
                buttonStartX, buttonStartY + 2 * buttonHeight + 2 * buttonSpacing, buttonWidth, buttonHeight,
                "Effects", "effects"
        ));
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
}