package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class SmashCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "smash_crit");

    @Override
    public void applyEffect(Player player, LivingIncomingDamageEvent event, int level) {

        float chance = level * 0.1f;
        float fallDistance = player.fallDistance;

        if(fallDistance <= 30) {
            chance += fallDistance/1.5f;
        } else if (fallDistance > 30) {
            chance += 0.2f;
        }

        if (fallDistance > 3 && player.level().random.nextFloat() < chance) {
            float damage = event.getAmount();

            float newDamage = 0;
            float blocks = fallDistance;

            // First 3 blocks deal 4 damage each
            float firstBlocks = Math.min(blocks, 3.0f);
            newDamage += firstBlocks * 4.0f;
            blocks -= firstBlocks;

            // Next 5 blocks deal 2 damage each
            if (blocks > 0) {
                float nextBlocks = Math.min(blocks, 5.0f);
                newDamage += nextBlocks * 2.0f;
                blocks -= nextBlocks;
            }

            // The rest of blocks deal 1 damage each
            if (blocks > 0) {
                newDamage += blocks;
            }

            // Now, we apply the reduction.
            newDamage = newDamage * 0.4f;

            event.setAmount(damage + newDamage);
            player.fallDistance = 0;

        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

