package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdMutateHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandMutate extends PlayerTargetCommand<CmdMutateHandler> {
	@Override
	public String getName() {
		return _("mutate");
	}

	@Override
	protected void handle(ICommandSender sender, CmdMutateHandler handler, Player target) {
		handler.mutate(target);
	}

	@Override
	protected boolean allowsNone() {
		return false;
	}
}
