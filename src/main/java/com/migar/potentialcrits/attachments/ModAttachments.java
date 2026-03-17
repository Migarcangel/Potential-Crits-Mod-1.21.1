package com.migar.potentialcrits.attachments;

import com.migar.potentialcrits.PotentialCrits;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.bus.api.IEventBus;
import com.mojang.serialization.Codec;

public class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, PotentialCrits.MODID);

    public static final DeferredHolder<AttachmentType<?>,AttachmentType<Integer>> PERMANENT_CHANCE =
            ATTACHMENT_TYPES.register(
                    "permanent_chance",
                    () -> AttachmentType.builder(() -> 0)
                            .serialize(Codec.INT)
                            .copyOnDeath()
                            .build()
            );

    public static final DeferredHolder<AttachmentType<?>,AttachmentType<Boolean>> PERMANENT_UPGRADE_1 =
            ATTACHMENT_TYPES.register(
                    "permanent_upgrade_1",
                    () -> AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL)
                            .copyOnDeath()
                            .build()
            );

    public static final DeferredHolder<AttachmentType<?>,AttachmentType<Boolean>> PERMANENT_UPGRADE_2 =
            ATTACHMENT_TYPES.register(
                    "permanent_upgrade_2",
                    () -> AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL)
                            .copyOnDeath()
                            .build()
            );

    public static final DeferredHolder<AttachmentType<?>,AttachmentType<Boolean>> PERMANENT_UPGRADE_3 =
            ATTACHMENT_TYPES.register(
                    "permanent_upgrade_3",
                    () -> AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL)
                            .copyOnDeath()
                            .build()
            );

    public static final DeferredHolder<AttachmentType<?>,AttachmentType<Boolean>> PERMANENT_UPGRADE_4 =
            ATTACHMENT_TYPES.register(
                    "permanent_upgrade_4",
                    () -> AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL)
                            .copyOnDeath()
                            .build()
            );

    public static final DeferredHolder<AttachmentType<?>,AttachmentType<Boolean>> PERMANENT_UPGRADE_5 =
            ATTACHMENT_TYPES.register(
                    "permanent_upgrade_5",
                    () -> AttachmentType.builder(() -> false)
                            .serialize(Codec.BOOL)
                            .copyOnDeath()
                            .build()
            );

    public static void register(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}