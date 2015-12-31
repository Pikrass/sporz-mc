package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdClearHandler;

import net.minecraft.command.ICommandSender;

public class CommandClear extends ActionCommand<CmdClearHandler> {
	@Override
	public String getName() {
		return _("clear");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("clear");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("clear");
	}

	@Override
	protected void preexecute(ICommandSender sender, String[] params) {
		sendMsg(sender, green(_("Your choice has been cleared")));
	}

	@Override
	protected void execute(ICommandSender sender, CmdClearHandler handler, String[] params) {
		handler.clear();
	}
}
