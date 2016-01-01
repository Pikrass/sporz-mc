package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdParalyseHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandParalyse extends PlayerTargetCommand<CmdParalyseHandler> {
	@Override
	public String getName() {
		return _("paralyse");
	}

	@Override
	protected void handle(ICommandSender sender, CmdParalyseHandler handler, Player target) {
		handler.paralyse(target);
	}

	@Override
	protected boolean allowsNone() {
		return false;
	}
}
