package com.migar.potentialcrits.client.gui;

import java.util.List;

public class BookModels {

    public static class BookData {
        public List<BookCategory> categories;
    }

    public static class BookCategory {
        public String title;
        public String content;  // Puede ser null si tiene subcategorías
        public List<BookCategory> subcategories;
    }

    public record BookSpread(String title, List<String> leftLines, List<String> rightLines) {}

    public static class NavigationButton {
        public int x, y, width, height;
        public String text;
        public String targetSectionId;

        public NavigationButton(int x, int y, int width, int height, String text, String targetSectionId) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.text = text;
            this.targetSectionId = targetSectionId;
        }

        public boolean isHovered(double mouseX, double mouseY) {
            return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        }
    }
}