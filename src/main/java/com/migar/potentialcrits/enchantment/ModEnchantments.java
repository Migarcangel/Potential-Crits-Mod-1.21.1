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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> FIRE_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "fire_crit"));

    public static final ResourceKey<Enchantment> THUNDER_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "thunder_crit"));

    public static final ResourceKey<Enchantment> DARK_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "dark_crit"));

    public static final ResourceKey<Enchantment> LIGHT_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "light_crit"));

    public static final ResourceKey<Enchantment> GROUND_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "ground_crit"));

    public static final ResourceKey<Enchantment> WATER_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "water_crit"));

    public static final ResourceKey<Enchantment> SUPER_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "super_crit"));

    public static final ResourceKey<Enchantment> TRUE_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "true_crit"));

    public static final ResourceKey<Enchantment> SHIELD_CRIT = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "shield_crit"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        // FIRE CRIT
        register(context, FIRE_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        2,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new FireCritEnchantmentEffect()));

        // THUNDER CRIT
        register(context, THUNDER_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        2,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new ThunderCritEnchantmentEffect()));

        // DARK CRIT
        register(context, DARK_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        5,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new DarkCritEnchantmentEffect()));

        // LIGHT CRIT
        register(context, LIGHT_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        5,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new LightCritEnchantmentEffect()));

        // GROUND CRIT
        register(context, GROUND_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        1,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new GroundCritEnchantmentEffect()));

        // WATER CRIT
        register(context, WATER_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        3,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new WaterCritEnchantmentEffect()));

        // SUPER CRIT
        register(context, SUPER_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        1,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new SuperCritEnchantmentEffect()));

        // TRUE CRIT
        register(context, TRUE_CRIT, Enchantment.enchantment(Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        1,
                        Enchantment.dynamicCost(10, 7),
                        Enchantment.dynamicCost(20, 7),
                        4,
                        EquipmentSlotGroup.MAINHAND))
                .withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM, new TrueCritEnchantmentEffect()));

        // SHIELD CRIT
        register(context, SHIELD_CRIT, Enchantment.enchantment(
                        Enchantment.definition(
                                items.getOrThrow(TagKey.create(Registries.ITEM,
                                        ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "enchantable/sword_shield"))),
                                4,
                                1,
                                Enchantment.dynamicCost(10, 7),
                                Enchantment.dynamicCost(20, 7),
                                4,
                                EquipmentSlotGroup.MAINHAND
                        ))
                .withEffect(
                        EnchantmentEffectComponents.POST_ATTACK,
                        EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.VICTIM,
                        new ShieldCritEnchantmentEffect()
                )
        );
    }

    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,
                                 Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
