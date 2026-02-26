package com.migar.potentialcrits.enchantment.crits;

import com.migar.potentialcrits.PotentialCrits;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ThunderCritEffect implements CritEffect {
    private static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(PotentialCrits.MODID, "thunder_crit");

    @Override
    public void applyEffect(Player player, LivingIncomingDamageEvent event, int level) {
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
            // It may apply additional damage because of the lightning itself. Not consistent though, only sometimes.
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.spawn(
                    (ServerLevel) player.level(),
                    target.getOnPos(),
                    MobSpawnType.TRIGGERED
            );

            if (lightning != null) {
                // Minimum damage. This is supposed to be unknown
                lightning.setDamage(0.1f);
                // Cancels the lightning fire
                lightning.getPersistentData().putBoolean("potentialcrits_no_fire", true);
            }

            float damage = event.getAmount();
            float newDamage = 3;
            if(thunder) {
                newDamage = 4.5f;
            }
            event.setAmount(damage + newDamage);

        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}

