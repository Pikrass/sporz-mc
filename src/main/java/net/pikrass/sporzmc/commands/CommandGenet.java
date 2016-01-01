package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdGenetHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandGenet extends ActionCommand<CmdGenetHandler> {
	@Override
	public String getName() {
		return _("genet");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("genet <player|none>");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("genet <player|none>");
	}

	@Override
	protected void execute(ICommandSender sender, CmdGenetHandler handler, String[] params) {
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
		handler.genet(target);
	}
}

