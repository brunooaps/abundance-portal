package com.abundanceportal.portal;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

// Debug/testing commands for the Abundance Portal mod.
public final class PortalCommands {
	private PortalCommands() {
	}

	public static void init() {
		CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) ->
			dispatcher.register(
				Commands.literal("forceportal")
					.requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
					.executes(PortalCommands::forcePortal)
			)
		);
	}

	private static int forcePortal(CommandContext<CommandSourceStack> context) {
		CommandSourceStack source = context.getSource();
		ServerLevel level = source.getServer().overworld();
		ServerPlayer target = source.getPlayer();

		boolean started = PortalEventManager.forceStart(level, target);
		if (started) {
			source.sendSuccess(() -> Component.literal("Abundance Portal: forcing the sky portal event now."), true);
		} else {
			source.sendFailure(
				Component.literal("Abundance Portal: couldn't force the event (one may already be active, or no clear-sky spot was found nearby).")
			);
		}

		return started ? 1 : 0;
	}
}
