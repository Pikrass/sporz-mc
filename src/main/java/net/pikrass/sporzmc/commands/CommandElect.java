package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdElectHandler;

import net.pikrass.sporz.actions.BlankVoteProhibitedException;
import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandElect extends PlayerTargetCommand<CmdElectHandler> {
	@Override
	public String getName() {
		return _("elect");
	}

	@Override
	protected void handle(ICommandSender sender, CmdElectHandler handler, Player target) {
		try {
			handler.elect(target);
		} catch(BlankVoteProhibitedException e) {
		}
	}

	@Override
	protected boolean allowsNone() {
		return false;
	}
}
