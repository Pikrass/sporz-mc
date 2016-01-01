package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;

import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

public abstract class PlayerTargetCommand<Handler> extends ActionCommand<Handler> {
	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		if(allowsNone())
			return getName()+" "+_("<player|none>");
		else
			return getName()+" "+_("<player>");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return getCommandShortUsage(sender);
	}

	@Override
	protected void execute(ICommandSender sender, Handler handler, String[] params) {
		if(params.length != 1) {
			printShortUsage(sender);
			return;
		}

		Player target;
		if(allowsNone() && params[0].equals(_("none")))
			target = Player.NOBODY;
		else
			target = SporzMC.getGame().getPlayer(params[0]);;

		if(target == null) {
			sendMsg(sender, red(_("This player doesn't exist")));
			return;
		}

		sendMsg(sender, green(_("Your choice has been saved")));
		handle(sender, handler, target);
	}

	protected abstract void handle(ICommandSender sender, Handler handler, Player target);
	protected abstract boolean allowsNone();
}
