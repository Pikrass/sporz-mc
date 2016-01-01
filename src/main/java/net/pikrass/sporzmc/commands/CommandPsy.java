package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdPsyHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandPsy extends PlayerTargetCommand<CmdPsyHandler> {
	@Override
	public String getName() {
		return _("psy");
	}

	@Override
	protected void handle(ICommandSender sender, CmdPsyHandler handler, Player target) {
		handler.psy(target);
	}

	@Override
	protected boolean allowsNone() {
		return true;
	}
}
