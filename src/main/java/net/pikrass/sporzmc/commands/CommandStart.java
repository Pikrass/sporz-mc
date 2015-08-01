package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.command.ICommandSender;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;

public class CommandStart extends SporzSubcommand
{
	@Override
	public String getName() {
		return _("start");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("start");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("start");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) {
		Game game = SporzMC.initGame();
		try {
			SporzMC.useCurrentPlayers();
			SporzMC.getRules().apply(game);
			SporzMC.startGame();
		} catch(RulesException e) {
			SporzMC.endGame();
			sendMsg(sender, red(_("There aren't enough players for the configured rules")));
		}
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return isOp(sender.getName());
	}
}

