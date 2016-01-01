package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdVoteHandler;

import net.pikrass.sporz.Player;
import net.pikrass.sporz.actions.InvalidChoiceException;

import net.minecraft.command.ICommandSender;

import java.util.Set;

public class CommandVote extends ActionCommand<CmdVoteHandler> {
	@Override
	public String getName() {
		return _("vote");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender, CmdVoteHandler handler) {
		return getUsage(handler);
	}

	@Override
	public String getCommandUsage(ICommandSender sender, CmdVoteHandler handler) {
		return getUsage(handler);
	}

	private String getUsage(CmdVoteHandler handler) {
		Set<Player> choices = handler.getVoteChoices();

		if(choices == null)
			return _("vote <player>|none");

		StringBuffer buf = new StringBuffer("vote ");
		for(Player player : choices) {
			if(player.isNobody())
				buf.append(_("none")+"|");
			else
				buf.append(player.getName()+"|");
		}

		return buf.substring(0, buf.length()-1).toString();
	}

	@Override
	protected void execute(ICommandSender sender, CmdVoteHandler handler, String[] params) {
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

		Set<Player> choices = handler.getVoteChoices();
		if(choices != null && !choices.contains(target)) {
			sendMsg(sender, red(_("This choice is unavailable")));
			return;
		}

		sendMsg(sender, green(_("Your choice has been saved")));
		try {
			handler.vote(target);
		} catch(InvalidChoiceException e) {
		}
	}
}
