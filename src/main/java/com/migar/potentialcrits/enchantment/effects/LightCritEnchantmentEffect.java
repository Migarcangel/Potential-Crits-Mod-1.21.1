package com.migar.potentialcrits.enchantment.effects;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record LightCritEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<LightCritEnchantmentEffect> CODEC = MapCodec.unit(LightCritEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel serverLevel, int enchLevel, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        // Find its real effect in enchantment/crits. This is because it depends of an event (check also ModEvents). You can't modify damage here.

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
