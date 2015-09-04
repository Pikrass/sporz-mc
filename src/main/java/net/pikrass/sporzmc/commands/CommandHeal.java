package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdHealHandler;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public class CommandHeal extends ActionCommand<CmdHealHandler> {
	@Override
	public String getName() {
		return _("heal");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("heal <player>");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("heal <player>");
	}

	@Override
	protected void execute(ICommandSender sender, CmdHealHandler handler, String[] params) {
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
		handler.heal(target);
	}
}

