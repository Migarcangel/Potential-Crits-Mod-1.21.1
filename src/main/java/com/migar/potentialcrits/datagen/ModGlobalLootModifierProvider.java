package com.migar.potentialcrits.datagen;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.item.ModItems;
import com.migar.potentialcrits.loot.AddItemModifier;
import com.migar.potentialcrits.loot.AddLootTableModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, PotentialCrits.MODID);
    }

    private record ItemEntry(String name, String target, float chance, Item item) {}
    private record ModifierEntry(String name, String target, float chance, String book) {}

    @Override
    protected void start() {

        ItemEntry[] items = {
                // CRIT COOKIES
                new ItemEntry("crit_cookie_ancient", "chests/ancient_city", 0.75f, ModItems.CRIT_COOKIE.get()),
                new ItemEntry("crit_cookie_mineshaft", "chests/abandoned_mineshaft", 0.5f, ModItems.CRIT_COOKIE.get()),
                new ItemEntry("crit_cookie_dungeon", "chests/simple_dungeon", 0.75f, ModItems.CRIT_COOKIE.get()),

                // CRIT PIES
                new ItemEntry("crit_pie_ancient", "chests/ancient_city", 0.15f, ModItems.CRIT_PIE.get()),
                new ItemEntry("crit_pie_mineshaft", "chests/abandoned_mineshaft", 0.1f, ModItems.CRIT_PIE.get()),
                new ItemEntry("crit_pie_dungeon", "chests/simple_dungeon", 0.1f, ModItems.CRIT_PIE.get()),
        };

        for (ItemEntry entry : items) {
            LootItemCondition[] conditions = new LootItemCondition[] {
                    new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace(entry.target)).build(),
                    LootItemRandomChanceCondition.randomChance(entry.chance).build()
            };

            this.add(entry.name, new AddItemModifier(conditions, entry.item));
        }

        ModifierEntry[] modifiers = {
                // CRIT GEMS
                new ModifierEntry("crit_gems", "chests/ancient_city", 1f, "chests/crit_gems"),
                new ModifierEntry("crit_gems", "chests/abandoned_mineshaft", 0.75f, "chests/crit_gems"),
                new ModifierEntry("crit_gems_dungeon", "chests/simple_dungeon", 0.75f, "chests/crit_gems"),
                new ModifierEntry("crit_gems_buried_treasure", "chests/buried_treasure", 1f, "chests/crit_gems"),
                new ModifierEntry("crit_gems_library", "chests/stronghold_library", 0.75f, "chests/crit_gems"),

                // RANDOM CRIT
                new ModifierEntry("random_crit_library", "chests/stronghold_library", 0.5f, "chests/random_crit_library"),

                // FIRE CRIT
                new ModifierEntry("fire_crit_nether_fortress", "chests/nether_bridge", 0.25f, "chests/fire_crit_book"),

                // SHIELD CRIT
                new ModifierEntry("shield_crit_guardian", "entities/elder_guardian", 0.5f, "chests/shield_crit_book"),
                new ModifierEntry("shield_crit_stronghold1", "chests/stronghold_corridor", 0.5f, "chests/shield_crit_book"),
                new ModifierEntry("shield_crit_stronghold2", "chests/stronghold_crossing", 0.5f, "chests/shield_crit_book"),

                // WATER CRIT
                new ModifierEntry("water_crit_ruins1", "chests/underwater_ruin_small", 0.5f, "chests/water_crit_book"),
                new ModifierEntry("water_crit_ruins2", "chests/underwater_ruin_big", 0.5f, "chests/water_crit_book"),

                // DARK CRIT
                new ModifierEntry("dark_crit_ancient", "chests/ancient_city", 0.5f, "chests/dark_crit_book_c"),
                new ModifierEntry("dark_crit_witch", "entities/witch", 0.5f, "chests/dark_crit_book_e"),
                new ModifierEntry("dark_crit_invocator", "entities/evoker", 0.5f, "chests/dark_crit_book_e"),
                // LIGHT CRIT
                // Find it in Trading Events

                // GROUND CRIT
                new ModifierEntry("ground_crit_mineshafts", "chests/abandoned_mineshaft", 0.5f, "chests/ground_crit_book"),

                // SUPER CRIT
                new ModifierEntry("super_crit_endcity", "chests/end_city_treasure", 0.5f, "chests/super_crit_book"),

                // TRUE CRIT
                new ModifierEntry("true_crit_trials", "chests/trial_chambers/reward_ominous_rare", 1f, "chests/true_crit_book"),

                // BERSERK CRIT
                new ModifierEntry("berserk_crit_bastion", "chests/bastion_treasure", 0.5f, "chests/berserk_crit_book"),
                new ModifierEntry("berserk_crit_brute", "entities/piglin_brute", 0.15f, "chests/berserk_crit_book"),

                // SMASH CRIT
                new ModifierEntry("smash_crit_trials", "chests/trial_chambers/reward_ominous_rare", 1f, "chests/smash_crit_book"),

                // UMBRAL CRIT
                new ModifierEntry("umbral_crit_dungeons", "chests/simple_dungeon", 0.75f, "chests/umbral_crit_book"),
                new ModifierEntry("umbral_crit_mineshaft", "chests/abandoned_mineshaft", 0.5f, "chests/umbral_crit_book"),

                // VAMPIRE CRIT
                new ModifierEntry("vampire_crit_ancient", "chests/ancient_city", 0.5f, "chests/vampire_crit_book_c"),
                new ModifierEntry("vampire_crit_mansion", "chests/woodland_mansion", 0.5f, "chests/vampire_crit_book_c"),
                new ModifierEntry("vampire_crit_bat", "entities/bat", 0.05f, "entities/vampire_crit_book_e"),

                // SUNDER CRIT
                new ModifierEntry("sunder_crit_vindicator", "entities/vindicator", 0.05f, "entities/sunder_crit_book"),
                new ModifierEntry("sunder_crit_brute", "entities/piglin_brute", 0.15f, "entities/sunder_crit_book"),

                // ICE CRIT
                new ModifierEntry("ice_crit_stray", "entities/stray", 0.15f, "entities/ice_crit_book_e"),
                new ModifierEntry("ice_crit_igloo", "chests/igloo_chest", 1, "entities/ice_crit_book_c"),

                // HARVEST CRIT
                new ModifierEntry("harvest_crit_ancient", "chests/ancient_city", 0.5f, "chests/harvest_crit_book")
        };

        for (ModifierEntry entry : modifiers) {
            LootItemCondition[] conditions;

            if (entry.chance > 0) {
                conditions = new LootItemCondition[] {
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace(entry.target)).build(),
                        LootItemRandomChanceCondition.randomChance(entry.chance).build()
                };
            } else {
                conditions = new LootItemCondition[] {
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace(entry.target)).build()
                };
            }

            this.add(entry.name,
                    new AddLootTableModifier(
                            conditions,
                            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, entry.book)
                    ));
        }
    }
}