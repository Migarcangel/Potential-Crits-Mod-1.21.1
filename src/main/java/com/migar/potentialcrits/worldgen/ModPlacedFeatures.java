package com.migar.potentialcrits.worldgen;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> CRIT_ORE_PLACED_KEY = registerKey();

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, configuredFeatures.getOrThrow(ModConfiguredFeatures.CRIT_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40),VerticalAnchor.absolute(20)))
                );

    }

    private static ResourceKey<PlacedFeature> registerKey() {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "crit_ore_placed"));
    }

    private static void register(BootstrapContext<PlacedFeature> context, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(ModPlacedFeatures.CRIT_ORE_PLACED_KEY, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
