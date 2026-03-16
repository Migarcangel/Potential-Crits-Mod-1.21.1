package com.migar.potentialcrits.effect;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, PotentialCrits.MODID);

    public static final Holder<MobEffect> HEAVY_EFFECT = MOB_EFFECTS.register("heavy",
            () -> new HeavyEffect(MobEffectCategory.HARMFUL, 0x36ebab)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "heavy"), -0.1f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> SHIELD_EFFECT = MOB_EFFECTS.register("shielded",
            () -> new ShieldEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));

    public static final Holder<MobEffect> BERSERKER_EFFECT = MOB_EFFECTS.register("berserker",
            () -> new BerserkerEffect(MobEffectCategory.BENEFICIAL, 0x36ebab)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE,
                            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "berserker"), 0.25f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> EXPOSED_EFFECT = MOB_EFFECTS.register("exposed",
            () -> new ExposedEffect(MobEffectCategory.HARMFUL, 0x36ebab)
                    .addAttributeModifier(Attributes.ARMOR,
                            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "exposed_armor"), -0.25f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(Attributes.ARMOR_TOUGHNESS,
                            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "exposed_toughness"), -0.25f,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final Holder<MobEffect> ACCURACY_EFFECT = MOB_EFFECTS.register("accuracy",
            () -> new AccuracyEffect(MobEffectCategory.BENEFICIAL, 0x36ebab));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
