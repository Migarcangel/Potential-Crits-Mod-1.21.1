package com.migar.potentialcrits.attachments;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;

public enum PermanentUpgrade {
    UPGRADE_1(ModAttachments.PERMANENT_UPGRADE_1, "UPGRADE I - ENDER SLAYER"),
    UPGRADE_2(ModAttachments.PERMANENT_UPGRADE_2, "UPGRADE II - TRIPLE CRIT"),
    UPGRADE_3(ModAttachments.PERMANENT_UPGRADE_3, "UPGRADE III - CRIT MASTER"),
    UPGRADE_4(ModAttachments.PERMANENT_UPGRADE_4, "UPGRADE IV - WITHER BANE"),
    UPGRADE_5(ModAttachments.PERMANENT_UPGRADE_5, "UPGRADE V - CRIT COMPLETIONIST");

    public final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> flag;
    public final String displayName;

    PermanentUpgrade(DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> flag, String displayName) {
        this.flag = flag;
        this.displayName = displayName;
    }
}