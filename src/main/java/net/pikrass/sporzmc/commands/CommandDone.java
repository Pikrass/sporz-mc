package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdDoneHandler;

import net.minecraft.command.ICommandSender;

public class CommandDone extends ActionCommand<CmdDoneHandler> {
	@Override
	public String getName() {
		return _("done");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("done");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("done");
	}

	@Override
	protected void execute(ICommandSender sender, CmdDoneHandler handler, String[] params) {
		sendMsg(sender, green(_("Your choice has been saved")));
		handler.done();
	}
}

