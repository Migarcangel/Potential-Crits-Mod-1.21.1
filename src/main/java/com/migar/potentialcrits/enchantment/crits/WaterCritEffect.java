package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class WaterCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "water_crit");

    @Override
    public void applyEffect(Player player, LivingDamageEvent.Pre event, int level) {
        LivingEntity target = event.getEntity();

        float chance = level * 0.05f;

        if (player.isInWaterRainOrBubble() && player.level().random.nextFloat() < chance) {
            float damage = event.getNewDamage();
            float newDamage = damage + 3;

            if(target.isInWaterRainOrBubble()) {
                newDamage = newDamage + 3;
            }

            boolean aquatic = target.getType().is(EntityTypeTags.AQUATIC) || target.getType() == EntityType.DROWNED;
            float multiplier = 1.0f;

            if(aquatic) {
                multiplier += 0.15f;
            }

            BlockPos pos = player.blockPosition();
            int depth = 0;
            BlockState block = player.level().getBlockState(pos);

            while(block.is(Blocks.WATER)) {
                pos = pos.above();
                block = player.level().getBlockState(pos);
                depth++;
            }

            if(depth >= 5 && depth < 15) multiplier += 0.05f;
            else if(depth >= 15 && depth < 30) multiplier += 0.10f;
            else if(depth >= 30) multiplier += 0.15f;

            event.setNewDamage(newDamage * multiplier);

        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

