package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandException;
import net.minecraft.util.BlockPos;

import net.pikrass.sporzmc.*;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class CommandPlayas extends SporzSubcommand
{
	@Override
	public String getName() {
		return _("playas");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("playas <player> <cmd> [args...]");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("playas <player> <cmd> [args...]");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) {
		if(params.length >= 2) {
			MCPlayer player = SporzMC.getPlayers().get(params[0]);
			if(player == null)
				printShortUsage(sender);
			else
				sendCommandAs(player, Arrays.copyOfRange(params, 1, params.length));
		} else {
			printShortUsage(sender);
		}
	}

	private void sendCommandAs(MCPlayer player, String[] args) {
		try {
			SporzMC.getCommand().execute(player.getCommandSender(), args);
		} catch(CommandException e) {
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> list = new LinkedList<String>();
		if(args.length == 1) {
			for(String name : SporzMC.getPlayers().keySet()) {
				if(name.startsWith(args[0]))
					list.add(name);
			}
		} else if(args.length >= 2) {
			MCPlayer player = SporzMC.getPlayers().get(args[0]);
			if(player != null)
				return SporzMC.getCommand().addTabCompletionOptions(
						player.getCommandSender(),
						Arrays.copyOfRange(args, 1, args.length), pos);
		}
		return list;
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if(!SporzMC.isStarted())
			return false;
		return SporzMC.isMaster(sender) || sender.canUseCommand(100, "sporz dev");
	}
}
