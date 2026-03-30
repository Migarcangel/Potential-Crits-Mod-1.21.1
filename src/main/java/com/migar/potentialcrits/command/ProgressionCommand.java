package com.migar.potentialcrits.command;

import com.migar.potentialcrits.attachments.PlayerData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class ProgressionCommand {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        var registryAccess = event.getBuildContext();

        dispatcher.register(Commands.literal("potentialcrits")
                .then(Commands.literal("progression")
                        .then(Commands.literal("set")
                                .requires(source -> source.hasPermission(4))
                                .then(Commands.argument("jugador", EntityArgument.players())
                                        .then(Commands.argument("tipo_crit", ResourceArgument.resource(registryAccess, Registries.ENCHANTMENT))
                                                .then(Commands.argument("cantidad", IntegerArgumentType.integer(0, 500))
                                                        .executes(ProgressionCommand::executeSetProgression)
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static int executeSetProgression(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer targetPlayer = EntityArgument.getPlayer(ctx, "jugador");

        var enchantmentRef = ResourceArgument.getResource(ctx, "tipo_crit", Registries.ENCHANTMENT);
        ResourceLocation critId = enchantmentRef.key().location();
        int amount = IntegerArgumentType.getInteger(ctx, "cantidad");

        CommandSourceStack source = ctx.getSource();

        PlayerData.setCritCount(targetPlayer, critId, amount);

        source.sendSuccess(() -> Component.literal("Progresión de " + critId.getPath() +
                " establecida a " + amount + " para " + targetPlayer.getName().getString()), true);

        targetPlayer.sendSystemMessage(Component.literal("§a[PotentialCrits] Tu progresión de " +
                critId.getPath() + " ha sido establecida a " + amount));

        return 1;
    }
}