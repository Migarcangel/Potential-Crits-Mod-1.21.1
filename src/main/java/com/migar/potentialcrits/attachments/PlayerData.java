package com.migar.potentialcrits.attachments;

import com.migar.potentialcrits.enchantment.crits.CritEffect;
import com.migar.potentialcrits.enchantment.crits.CritRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class PlayerData {

    public static final int CRIT_UPGRADE_1_REQUIREMENT = 100;
    public static final int CRIT_UPGRADE_2_REQUIREMENT = 250;
    public static final int CRIT_UPGRADE_3_REQUIREMENT = 500;

    private static final int PERM_UPGRADE_3_REQUIREMENT = 1000;

    // ========== CRIT PROGRESS ==========

    public static void incrementCritCount(Player player, ResourceLocation critId) {
        if (player == null) return;

        CompoundTag progress = player.getData(ModAttachments.CRITS_PROGRESS.get());

        String key = critId.toString();
        int current = progress.getInt(key);
        int newCount = current + 1;
        progress.putInt(key, newCount);

        player.setData(ModAttachments.CRITS_PROGRESS.get(), progress);

        checkCritProgression(player, critId, newCount);
        checkAllCritsHaveProgression(player);
    }

    private static void checkCritProgression(Player player, ResourceLocation critId, int critCount) {
        int color;
        if (critCount == CRIT_UPGRADE_3_REQUIREMENT) {
            color = 0xFFAA00; // Golden
        }
        else if (critCount == CRIT_UPGRADE_2_REQUIREMENT) {
            color = 0x55FFFF; // Cyan
        }
        else if (critCount == CRIT_UPGRADE_1_REQUIREMENT) {
            color = 0xAAAAAA; // Gray
        }
        else {
            return;
        }

        String critKey = critId.getPath();

        Component critName = Component.translatable("enchantment.potentialcrits." + critKey);
        Component unlockedUpgrade = Component.translatable("crit.progress.potentialcrits.unlocked");

        player.displayClientMessage(
                unlockedUpgrade.copy().withColor(0x55FF55)
                .withStyle(net.minecraft.ChatFormatting.BOLD),
                false
        );

        player.displayClientMessage(
                critName.copy()
                        .withColor(color)
                        .append(Component.literal(" : " + critCount + " crits.")
                                .withColor(color)),
                false
        );
    }

    public static int getCritCount(Player player, ResourceLocation critId) {
        if (player == null) return 0;
        CompoundTag progress = player.getData(ModAttachments.CRITS_PROGRESS.get());
        return progress.getInt(critId.toString());
    }

    public static int getUpgradeLevel(Player player, ResourceLocation critId) {
        int critCount = getCritCount(player, critId);
        if (critCount >= CRIT_UPGRADE_3_REQUIREMENT) return 3;
        if (critCount >= CRIT_UPGRADE_2_REQUIREMENT) return 2;
        if (critCount >= CRIT_UPGRADE_1_REQUIREMENT) return 1;
        return 0;
    }

    public static void setCritCount(Player player, ResourceLocation critId, int critCount) {
        CompoundTag progress = player.getData(ModAttachments.CRITS_PROGRESS.get());

        String key = critId.toString();
        progress.putInt(key, critCount);

        player.setData(ModAttachments.CRITS_PROGRESS.get(), progress);
    }

    // ========== PERMANENT UPGRADES ==========

    public static int getPermanentChance(Player player) {
        if (player == null) return 0;
        return player.getData(ModAttachments.PERMANENT_CHANCE.get());
    }

    public static void setPermanentUpgrade(Player player, PermanentUpgrade upgrade) {
        if (player == null) return;
        if (player.getData(upgrade.flag.get())) return;

        player.setData(upgrade.flag.get(), true);

        int oldChance = player.getData(ModAttachments.PERMANENT_CHANCE.get());
        int newChance = oldChance + 1;
        player.setData(ModAttachments.PERMANENT_CHANCE.get(), newChance);

        player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(
                        "§a§l✦ " + upgrade.displayName + " UNLOCKED! ✦§r"
                ),
                false
        );

        player.displayClientMessage(
                net.minecraft.network.chat.Component.literal(
                        "§7Permanent crit chance: §e" + oldChance + "% §7→ §a" + newChance + "%§r"
                ),
                false
        );

        player.level().playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_LEVELUP,
                SoundSource.PLAYERS,
                1.0f,
                1.0f
        );
    }

    // ========== PERMANENT UPGRADES 3 & 5 ==========

    public static int getTotalCrits(Player player) {
        if (player == null) return 0;
        return player.getData(ModAttachments.TOTAL_CRITS.get());
    }

    public static void incTotalCrits(Player player) {
        if (player == null) return;
        int newCrits = player.getData(ModAttachments.TOTAL_CRITS.get()) + 1;

        player.setData(ModAttachments.TOTAL_CRITS.get(), newCrits);

        if (newCrits >= PERM_UPGRADE_3_REQUIREMENT) {
            setPermanentUpgrade(player, PermanentUpgrade.UPGRADE_3);
        }
    }

    private static void checkAllCritsHaveProgression(Player player) {
        if (player.getData(ModAttachments.PERMANENT_UPGRADE_5.get())) return;

        CompoundTag progress = player.getData(ModAttachments.CRITS_PROGRESS.get());

        for (CritEffect critEffect : CritRegistry.getAll()) {
            String key = critEffect.getId().toString();
            if (progress.getInt(key) <= 0) {
                return;
            }
        }

        setPermanentUpgrade(player, PermanentUpgrade.UPGRADE_5);
    }
}