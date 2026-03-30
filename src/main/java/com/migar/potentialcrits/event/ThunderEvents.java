package com.migar.potentialcrits.event;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ThunderEvents {

    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getSource().is(DamageTypeTags.IS_LIGHTNING)) {
                AABB area = player.getBoundingBox().inflate(3.0);
                java.util.List<LightningBolt> lightnings = player.level().getEntitiesOfClass(
                        LightningBolt.class,
                        area,
                        bolt -> bolt.getPersistentData().getBoolean("potentialcrits_ignore_player")
                );

                if (!lightnings.isEmpty()) {
                    event.setCanceled(true);
                }
            }
        }
    }
}