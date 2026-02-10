package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Objects;

public class ThunderCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "thunder_crit");

    @Override
    public void applyEffect(Player player, LivingDamageEvent.Pre event, int level) {
        LivingEntity target = event.getEntity();

        BlockPos pos = target.blockPosition().above();
        boolean belowSky = target.level().canSeeSky(pos);

        if(!belowSky) { // If target is not below the sky, it won't trigger
            return;
        }

        boolean thunder = player.level().isThundering();

        float chance = level * 0.1f;
        if (thunder) {
            chance = chance * 2;
        }

        if (player.level().random.nextFloat() < chance) {
            // It may apply 5 additional damage because of the lightning itself. Not consistent though, only sometimes.
            Objects.requireNonNull(EntityType.LIGHTNING_BOLT.spawn(
                    (ServerLevel) player.level(),
                    target.getOnPos(),
                    MobSpawnType.TRIGGERED
            )).setVisualOnly(true);

            float damage = event.getNewDamage();
            float newDamage = 3;
            if(thunder) {
                newDamage = 4.5f;
            }
            event.setNewDamage(damage + newDamage);

        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

