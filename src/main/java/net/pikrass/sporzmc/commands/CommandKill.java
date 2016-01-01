package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdKillHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandKill extends PlayerTargetCommand<CmdKillHandler> {
	@Override
	public String getName() {
		return _("kill");
	}

	@Override
	protected void handle(ICommandSender sender, CmdKillHandler handler, Player target) {
		handler.kill(target);
	}

	@Override
	protected boolean allowsNone() {
		return false;
	}
}
