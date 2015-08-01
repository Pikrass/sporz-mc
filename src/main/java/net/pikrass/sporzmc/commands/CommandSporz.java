package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandException;
import net.minecraft.util.BlockPos;

import net.pikrass.sporzmc.*;

public class CommandSporz extends CommandBase
{
	private final HashMap<String, SporzSubcommand> subCommands =
		new HashMap<String, SporzSubcommand>();

	public CommandSporz() {
		LinkedList<SporzSubcommand> cmds = new LinkedList<SporzSubcommand>();
		cmds.add(new CommandPlayers());
		cmds.add(new CommandRules());
		cmds.add(new CommandStart());

		if(SporzMC.devMode())
			cmds.add(new CommandDev());

		for(SporzSubcommand cmd : cmds)
			subCommands.put(cmd.getName(), cmd);
	}

	@Override
	public String getName() {
		return "sporz";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("/sporz <command|help>");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) throws CommandException {
		if(params.length == 0 || (params.length == 1 && params[0].equals(_("help")))) {
			printHelp(sender);
			return;
		}

		boolean help = false;
		SporzSubcommand cmd;

		if(params.length > 1 && params[0].equals(_("help"))) {
			cmd = subCommands.get(params[1]);
			help = true;
		} else {
			cmd = subCommands.get(params[0]);
		}

		if(cmd == null || !cmd.canCommandSenderUse(sender)) {
			sendMsg(sender, red(_("Unknown command. Try /sporz help for a list of commands")));
		} else {
			if(help)
				cmd.printUsage(sender);
			else
				cmd.execute(sender, Arrays.copyOfRange(params, 1, params.length));
		}
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if(args.length > 1) {
			SporzSubcommand cmd = subCommands.get(args[0]);
			if(cmd != null && cmd.canCommandSenderUse(sender))
				return cmd.addTabCompletionOptions(sender,
						Arrays.copyOfRange(args, 1, args.length), pos);
			else
				return new LinkedList<String>();
		}

		List<String> list = new LinkedList<String>();

		for(String sub : subCommands.keySet()) {
			if(subCommands.get(sub).canCommandSenderUse(sender) && sub.startsWith(args[0]))
				list.add(sub);
		}

		return list;
	}

	public void printHelp(ICommandSender sender) {
		sendMsg(sender, dgreen(_("Available sporz commands:")));

		for(String sub : subCommands.keySet()) {
			SporzSubcommand cmd = subCommands.get(sub);

			if(cmd.canCommandSenderUse(sender))
				cmd.printSummary(sender);
		}
	}
}
