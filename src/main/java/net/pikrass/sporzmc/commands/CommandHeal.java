package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdHealHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandHeal extends PlayerTargetCommand<CmdHealHandler> {
	@Override
	public String getName() {
		return _("heal");
	}

	@Override
	protected void handle(ICommandSender sender, CmdHealHandler handler, Player target) {
		handler.heal(target);
	}

	@Override
	protected boolean allowsNone() {
		return false;
	}
}
