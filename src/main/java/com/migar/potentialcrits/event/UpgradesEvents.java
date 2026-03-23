package com.migar.potentialcrits.event;

import com.migar.potentialcrits.attachments.PermanentUpgrade;
import com.migar.potentialcrits.attachments.PlayerData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import static com.migar.potentialcrits.event.ModEvents.WAS_CRIT;

public class UpgradesEvents {


    public static void onKill(LivingDeathEvent event) {
        if(event.getSource().getEntity() instanceof Player player) {
            if(event.getEntity().getType() == EntityType.ENDER_DRAGON) {
                PlayerData.setPermanentUpgrade(player, PermanentUpgrade.UPGRADE_1);
            } else if(event.getEntity().getType() == EntityType.WITHER && WAS_CRIT.get()) {
                WAS_CRIT.remove();
                PlayerData.setPermanentUpgrade(player, PermanentUpgrade.UPGRADE_4);
            }
        }
    }
}
