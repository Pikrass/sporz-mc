package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdSpyHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandSpy extends ActionCommand<CmdSpyHandler> {
	@Override
	public String getName() {
		return _("spy");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("spy <player|none>");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("spy <player|none>");
	}

	@Override
	protected void execute(ICommandSender sender, CmdSpyHandler handler, String[] params) {
		if(params.length != 1) {
			printShortUsage(sender);
			return;
		}

		Player target;
		if(params[0].equals(_("none")))
			target = Player.NOBODY;
		else
			target = SporzMC.getGame().getPlayer(params[0]);;

		if(target == null) {
			sendMsg(sender, red(_("This player doesn't exist")));
			return;
		}

		sendMsg(sender, green(_("Your choice has been saved")));
		handler.spy(target);
	}
}

