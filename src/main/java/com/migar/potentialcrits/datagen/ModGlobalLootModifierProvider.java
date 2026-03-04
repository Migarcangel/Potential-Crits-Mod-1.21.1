package com.migar.potentialcrits.datagen;

import com.migar.potentialcrits.PotentialCrits;
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

    @Override
    protected void start() {

        // FIRE CRIT
        this.add("fire_crit_nether_fortress",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/nether_bridge")).build(),
                                LootItemRandomChanceCondition.randomChance(0.25f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/fire_crit_book")
                ));

        // SHIELD CRIT
        this.add("shield_crit_guardian",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/elder_guardian")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/shield_crit_book")
                ));

        this.add("shield_crit_stronghold1",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/stronghold_corridor")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/shield_crit_book")
                ));

        this.add("shield_crit_stronghold2",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/stronghold_crossing")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/shield_crit_book")
                ));

        // WATER CRIT
        this.add("water_crit_ruins1",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/underwater_ruin_small")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/water_crit_book")
                ));

        this.add("water_crit_ruins2",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/underwater_ruin_big")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/water_crit_book")
                ));

        // DARK CRIT
        this.add("dark_crit_ancient",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/ancient_city")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/dark_crit_book_c")
                ));

        this.add("dark_crit_witch",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/witch")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/dark_crit_book_e")
                ));
        this.add("dark_crit_invocator",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/evoker")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/dark_crit_book_e")
                ));
        // Light Crit Clérigo

        // GROUND CRIT
        this.add("ground_crit_mineshafts",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/abandoned_mineshaft")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/ground_crit_book")
                ));

        // SUPER CRIT
        this.add("super_crit_endcity",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/end_city_treasure")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/super_crit_book")
                ));

        // BERSERK CRIT
        this.add("berserk_crit_bastion",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/bastion_treasure")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/berserk_crit_book")
                ));

        this.add("berserk_crit_brute",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/piglin_brute")).build(),
                                LootItemRandomChanceCondition.randomChance(0.15f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/berserk_crit_book")
                ));

        // SMASH CRIT
        this.add("smash_crit_trials",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/trial_chambers/reward_ominous_rare")).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/smash_crit_book")
                ));

        // UMBRAL CRIT
        this.add("umbral_crit_dungeons",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/simple_dungeon")).build(),
                                LootItemRandomChanceCondition.randomChance(0.75f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/umbral_crit_book")
                ));

        this.add("umbral_crit_dungeons",
                new AddLootTableModifier(
                        new LootItemCondition[] {
                                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/abandoned_mineshaft")).build(),
                                LootItemRandomChanceCondition.randomChance(0.5f).build()
                        },
                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "chests/umbral_crit_book")
                ));
    }
}
