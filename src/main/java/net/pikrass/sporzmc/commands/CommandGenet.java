package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdGenetHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandGenet extends PlayerTargetCommand<CmdGenetHandler> {
	@Override
	public String getName() {
		return _("genet");
	}

	@Override
	protected void handle(ICommandSender sender, CmdGenetHandler handler, Player target) {
		handler.genet(target);
	}

	@Override
	protected boolean allowsNone() {
		return true;
	}
}
