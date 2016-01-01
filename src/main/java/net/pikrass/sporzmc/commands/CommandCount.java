package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdCountHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandCount extends ActionCommand<CmdCountHandler> {
	@Override
	public String getName() {
		return _("count");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("count <yes|no>");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("count <yes|no>");
	}

	@Override
	protected void execute(ICommandSender sender, CmdCountHandler handler, String[] params) {
		if(params.length != 1) {
			printShortUsage(sender);
			return;
		}

		boolean choice;
		if(params[0].equals(_("yes")))
			choice = true;
		else if(params[0].equals(_("no")))
			choice = false;
		else {
			sendMsg(sender, red(_("Please choose between yes or no")));
			return;
		}

		sendMsg(sender, green(_("Your choice has been saved")));
		handler.count(choice);
	}
}

