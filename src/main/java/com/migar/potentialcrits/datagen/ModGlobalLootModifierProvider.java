package com.migar.potentialcrits.datagen;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.item.ModItems;
import com.migar.potentialcrits.loot.AddItemModifier;
import com.migar.potentialcrits.loot.AddLootTableModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, PotentialCrits.MODID);
    }

    private record ModifierEntry(String name, String target, float chance, String book) {}

    @Override
    protected void start() {

        this.add("crit_gem_ancient",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/ancient_city")).build(),
                        LootItemRandomChanceCondition.randomChance(0.25f).build()
                }, ModItems.CRIT_GEM.get()));

        ModifierEntry[] modifiers = {
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
                new ModifierEntry("sunder_crit_brute", "entities/piglin_brute", 0.15f, "entities/sunder_crit_book")
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