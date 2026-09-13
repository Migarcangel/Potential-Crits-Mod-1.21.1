package com.migar.potentialcrits.client.gui;

import com.google.gson.Gson;
import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

public class BookDataLoader {

    public BookModels.BookData loadBookData() {
        return loadRawBookData();
    }

    private BookModels.BookData loadRawBookData() {
        String lang = Minecraft.getInstance().getLanguageManager().getSelected();
        String langCode = lang.startsWith("es_") ? "es_es" : "en_us";
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "book/" + langCode + ".json");

        try {
            Optional<Resource> resourceOptional = Minecraft.getInstance().getResourceManager().getResource(location);
            if (resourceOptional.isPresent()) {
                Resource resource = resourceOptional.get();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                    Gson gson = new Gson();
                    return gson.fromJson(reader, BookModels.BookData.class);
                }
            } else {
                return loadFallbackEnglish();
            }
        } catch (Exception e) {
            PotentialCrits.LOGGER.error("Error loading book data", e);
            return loadFallbackEnglish();
        }
    }

    private BookModels.BookData loadFallbackEnglish() {
        try {
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "book/en_us.json");
            Optional<Resource> resourceOptional = Minecraft.getInstance().getResourceManager().getResource(location);
            if (resourceOptional.isPresent()) {
                Resource resource = resourceOptional.get();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                    Gson gson = new Gson();
                    return gson.fromJson(reader, BookModels.BookData.class);
                }
            } else {
                return createDefaultBookData();
            }
        } catch (Exception e) {
            PotentialCrits.LOGGER.error("Error loading fallback english book data", e);
            return createDefaultBookData();
        }
    }

    private BookModels.BookData createDefaultBookData() {
        BookModels.BookData defaultData = new BookModels.BookData();
        defaultData.categories = new ArrayList<>();

        // Introduction
        BookModels.BookCategory intro = new BookModels.BookCategory();
        intro.title = "Introduction";
        intro.content = "Welcome to Potential Crits! This book will guide you through the mod's features. " +
                "General Info will teach you about mechanics, compatibilities, and obtainments. " +
                "List of Crits will show all available crits in the mod. " +
                "Click on the left or right side of the book to flip pages.";
        intro.subcategories = new ArrayList<>();

        // General Info
        BookModels.BookCategory generalInfo = new BookModels.BookCategory();
        generalInfo.title = "General Info";
        generalInfo.content = null;
        generalInfo.subcategories = new ArrayList<>();

        BookModels.BookCategory mechanics = new BookModels.BookCategory();
        mechanics.title = "Mechanics";
        mechanics.content = "Crits are special effects that trigger when attacking enemies. Each crit has a chance to activate based on your luck stat and equipment. Crits can provide various benefits such as extra damage, status effects, healing, and resource generation.";
        mechanics.subcategories = new ArrayList<>();

        BookModels.BookCategory compatibilities = new BookModels.BookCategory();
        compatibilities.title = "Compatibilities";
        compatibilities.content = "This mod works well with other combat enhancement mods, RPG-style progression systems, and magic and spellcasting mods. Potential Crits is designed to be compatible with most mods that don't fundamentally change the combat system.";
        compatibilities.subcategories = new ArrayList<>();

        BookModels.BookCategory obtainments = new BookModels.BookCategory();
        obtainments.title = "Obtainments";
        obtainments.content = "You can obtain crit books from dungeon chests with a 15 percent chance, boss drops with a 30 percent chance, trading with librarians, and crafting with rare materials. Higher tier crits are rarer and more powerful!";
        obtainments.subcategories = new ArrayList<>();

        generalInfo.subcategories.add(mechanics);
        generalInfo.subcategories.add(compatibilities);
        generalInfo.subcategories.add(obtainments);

        // List of Crits
        BookModels.BookCategory listOfCrits = new BookModels.BookCategory();
        listOfCrits.title = "List of Crits";
        listOfCrits.content = "Coming soon! Here will be a complete list of all available crits. Fire Crit sets enemies on fire. Ice Crit slows enemy movement. Thunder Crit chains lightning to nearby enemies. Vampiric Crit heals the attacker. Critical Strike deals double damage. Execute Crit instantly kills low health enemies. Shield Break Crit ignores armor. Poison Crit applies poison damage over time. More crits will be added in future updates!";
        listOfCrits.subcategories = new ArrayList<>();

        defaultData.categories.add(intro);
        defaultData.categories.add(generalInfo);
        defaultData.categories.add(listOfCrits);

        return defaultData;
    }
}