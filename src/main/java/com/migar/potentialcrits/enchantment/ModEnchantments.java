package com.migar.potentialcrits.enchantment;

import com.migar.potentialcrits.PotentialCrits;
import com.migar.potentialcrits.enchantment.effects.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModEnchantments {
    // ResourceKeys
    private static final List<ResourceKey<Enchantment>> CRITS = new ArrayList<>();
    public static List<ResourceKey<Enchantment>> getAllCrits() {
        return Collections.unmodifiableList(CRITS);
    }

    private static ResourceKey<Enchantment> createCrit(String name) {
        ResourceKey<Enchantment> key = ResourceKey.create(
                Registries.ENCHANTMENT,
                ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, name)
        );

        CRITS.add(key);
        return key;
    }

    public static final ResourceKey<Enchantment> FIRE_CRIT = createCrit("fire_crit");
    public static final ResourceKey<Enchantment> THUNDER_CRIT = createCrit("thunder_crit");
    public static final ResourceKey<Enchantment> DARK_CRIT = createCrit("dark_crit");
    public static final ResourceKey<Enchantment> LIGHT_CRIT = createCrit("light_crit");
    public static final ResourceKey<Enchantment> GROUND_CRIT = createCrit("ground_crit");
    public static final ResourceKey<Enchantment> WATER_CRIT = createCrit("water_crit");
    public static final ResourceKey<Enchantment> SUPER_CRIT = createCrit("super_crit");
    public static final ResourceKey<Enchantment> TRUE_CRIT = createCrit("true_crit");
    public static final ResourceKey<Enchantment> SHIELD_CRIT = createCrit("shield_crit");
    public static final ResourceKey<Enchantment> BERSERK_CRIT = createCrit("berserk_crit");
    public static final ResourceKey<Enchantment> SMASH_CRIT = createCrit("smash_crit");
    public static final ResourceKey<Enchantment> UMBRAL_CRIT = createCrit("umbral_crit");
    public static final ResourceKey<Enchantment> VAMPIRE_CRIT = createCrit("vampire_crit");
    public static final ResourceKey<Enchantment> SUNDER_CRIT = createCrit("sunder_crit");

    // Item Tags
    public static final TagKey<Item> SWORD_SHIELD =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "enchantable/sword_shield"));

    public static final TagKey<Item> SWORD_TRIDENT =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "enchantable/sword_trident"));

    public static final TagKey<Item> SWORD_AXE =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "enchantable/sword_axe"));

    public static final TagKey<Item> SWORD_AXE_PICKAXE_SHOVEL =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "enchantable/sword_axe_pickaxe_shovel"));

    public static final TagKey<Item> SWORD_AXE_TRIDENT =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "enchantable/sword_axe_trident"));

    public static final TagKey<Item> SWORD_AXE_TRIDENT_MACE =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "enchantable/sword_axe_trident_mace"));

    public static final TagKey<Item> AXE_MACE =
            TagKey.create(Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "enchantable/axe_mace"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        // FIRE CRIT
        register(context, FIRE_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        2,
                        2,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(20, 10),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.FIRE_WATER_EXCLUSIVE))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.FIRE_UMBRAL_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new FireCritEnchantmentEffect()));

        // THUNDER CRIT
        register(context, THUNDER_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_TRIDENT),
                        items.getOrThrow(SWORD_TRIDENT),
                        3,
                        2,
                        Enchantment.dynamicCost(10, 10),
                        Enchantment.dynamicCost(20, 10),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.THUNDER_UMBRAL_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new ThunderCritEnchantmentEffect()));

        // DARK CRIT
        register(context, DARK_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_AXE_TRIDENT_MACE),
                        items.getOrThrow(SWORD_AXE_TRIDENT_MACE),
                        2,
                        5,
                        Enchantment.dynamicCost(15, 10),
                        Enchantment.dynamicCost(30, 10),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.DARK_LIGHT_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new DarkCritEnchantmentEffect()));

        // LIGHT CRIT
        register(context, LIGHT_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_AXE_TRIDENT_MACE),
                        items.getOrThrow(SWORD_AXE_TRIDENT_MACE),
                        2,
                        5,
                        Enchantment.dynamicCost(15, 10),
                        Enchantment.dynamicCost(30, 10),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.DARK_LIGHT_EXCLUSIVE))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.LIGHT_UMBRAL_EXCLUSIVE))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.VAMPIRE_LIGHT_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new LightCritEnchantmentEffect()));

        // GROUND CRIT
        register(context, GROUND_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_AXE_PICKAXE_SHOVEL),
                        items.getOrThrow(SWORD_AXE_PICKAXE_SHOVEL),
                        2,
                        1,
                        Enchantment.dynamicCost(20, 7),
                        Enchantment.dynamicCost(30, 7),
                        10,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.GROUND_SUPER_TRUE_SMASH_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new GroundCritEnchantmentEffect()));

        // WATER CRIT
        register(context, WATER_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_TRIDENT),
                        items.getOrThrow(SWORD_TRIDENT),
                        4,
                        3,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.FIRE_WATER_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new WaterCritEnchantmentEffect()));

        // SUPER CRIT
        register(context, SUPER_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_AXE_TRIDENT_MACE),
                        items.getOrThrow(SWORD_AXE_TRIDENT_MACE),
                        1,
                        5,
                        Enchantment.dynamicCost(15, 10),
                        Enchantment.dynamicCost(30, 10),
                        6,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.GROUND_SUPER_TRUE_SMASH_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new SuperCritEnchantmentEffect()));

        // TRUE CRIT
        register(context, TRUE_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_AXE_TRIDENT),
                        items.getOrThrow(SWORD_AXE_TRIDENT),
                        5,
                        5,
                        Enchantment.dynamicCost(15, 10),
                        Enchantment.dynamicCost(30, 10),
                        6,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.GROUND_SUPER_TRUE_SMASH_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new TrueCritEnchantmentEffect()));

        // SHIELD CRIT
        register(context, SHIELD_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_SHIELD),
                        items.getOrThrow(SWORD_SHIELD),
                                2,
                                1,
                        Enchantment.dynamicCost(20, 7),
                        Enchantment.dynamicCost(30, 7),
                        10,
                                EquipmentSlotGroup.MAINHAND
                        ))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.SHIELD_BERSERK_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new ShieldCritEnchantmentEffect()));

        // BERSERK CRIT
        register(context, BERSERK_CRIT , Enchantment.enchantment(
                        Enchantment.definition(
                                items.getOrThrow(SWORD_AXE),
                                items.getOrThrow(SWORD_AXE),
                                2,
                                1,
                                Enchantment.dynamicCost(20, 7),
                                Enchantment.dynamicCost(30, 7),
                                10,
                                EquipmentSlotGroup.MAINHAND
                        ))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.SHIELD_BERSERK_EXCLUSIVE))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.VAMPIRE_BERSERK_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new BerserkCritEnchantmentEffect()));

        // SMASH CRIT
        register(context, SMASH_CRIT , Enchantment.enchantment(
                        Enchantment.definition(
                                items.getOrThrow(SWORD_AXE),
                                items.getOrThrow(SWORD_AXE),
                                1,
                                4,
                                Enchantment.dynamicCost(15, 10),
                                Enchantment.dynamicCost(30, 10),
                                5,
                                EquipmentSlotGroup.MAINHAND
                        ))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.GROUND_SUPER_TRUE_SMASH_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new SmashCritEnchantmentEffect()));

        // UMBRAL CRIT
        register(context, UMBRAL_CRIT , Enchantment.enchantment(
                        Enchantment.definition(
                                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                                4,
                                3,
                                Enchantment.dynamicCost(10, 7),
                                Enchantment.dynamicCost(20, 7),
                                4,
                                EquipmentSlotGroup.MAINHAND
                        ))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.FIRE_UMBRAL_EXCLUSIVE))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.THUNDER_UMBRAL_EXCLUSIVE))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.LIGHT_UMBRAL_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new UmbralCritEnchantmentEffect()));

        // VAMPIRE CRIT
        register(context, VAMPIRE_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(SWORD_AXE_TRIDENT),
                        items.getOrThrow(SWORD_AXE_TRIDENT),
                        2,
                        4,
                        Enchantment.dynamicCost(15, 10),
                        Enchantment.dynamicCost(30, 10),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.VAMPIRE_LIGHT_EXCLUSIVE))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.VAMPIRE_BERSERK_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new VampireCritEnchantmentEffect()));

        // SUNDER CRIT
        register(context, SUNDER_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(AXE_MACE),
                        items.getOrThrow(AXE_MACE),
                        2,
                        4,
                        Enchantment.dynamicCost(15, 10),
                        Enchantment.dynamicCost(30, 10),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.SUNDER_TRUE_EXCLUSIVE))
                .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.SUNDER_SHIELD_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new SunderCritEnchantmentEffect()));
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
