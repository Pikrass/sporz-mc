package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdMutateHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandMutate extends ActionCommand<CmdMutateHandler> {
	@Override
	public String getName() {
		return _("mutate");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("mutate <player>");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("mutate <player>");
	}

	@Override
	protected void execute(ICommandSender sender, CmdMutateHandler handler, String[] params) {
		if(params.length != 1) {
			printShortUsage(sender);
			return;
		}

		Player target = SporzMC.getGame().getPlayer(params[0]);;

		if(target == null) {
			sendMsg(sender, red(_("This player doesn't exist")));
			return;
		}

		sendMsg(sender, green(_("Your choice has been saved")));
		handler.mutate(target);
	}
}
