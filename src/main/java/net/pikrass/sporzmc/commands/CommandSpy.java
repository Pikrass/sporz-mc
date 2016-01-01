package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdSpyHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandSpy extends PlayerTargetCommand<CmdSpyHandler> {
	@Override
	public String getName() {
		return _("spy");
	}

	@Override
	protected void handle(ICommandSender sender, CmdSpyHandler handler, Player target) {
		handler.spy(target);
	}

	@Override
	protected boolean allowsNone() {
		return true;
	}
}
