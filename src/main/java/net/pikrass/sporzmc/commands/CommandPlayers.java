package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import net.pikrass.sporzmc.*;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;

public class CommandPlayers extends SporzSubcommand
{
	@Override
	public String getName() {
		return _("players");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("players init OR <add|remove> <player>");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return
			_("players")+"\n"+
			_("players init")+"\n"+
			_("players add <player>")+"\n"+
			_("players remove <player>");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) {
		if(params.length == 0) {
			showPlayers(sender);
		} else if(params[0].equals(_("init"))) {
			initPlayers(sender);
		} else if(params.length == 2 && params[0].equals(_("add"))) {
			addPlayer(sender, params[1]);
		} else if(params.length == 2 && params[0].equals(_("remove"))) {
			removePlayer(sender, params[1]);
		} else {
			printShortUsage(sender);
		}
	}

	private void showPlayers(ICommandSender sender) {
		Map<String, MCPlayer> players = SporzMC.getPlayers();

		if(players.isEmpty()) {
			sendMsg(sender, _("(No players)"));
			return;
		}

		StringBuffer message = new StringBuffer();

		for(MCPlayer player : players.values()) {
			message.append(player.getName());
			message.append("\n");
		}

		sendMsg(sender, message.substring(0, message.length()-1));
	}

	private void initPlayers(ICommandSender sender) {
		String[] names = MinecraftServer.getServer().getAllUsernames();
		Map<String, MCPlayer> players = SporzMC.getPlayers();

		players.clear();

		for(String name : names) {
			if(!name.equals(sender.getName()))
				players.put(name, new MCPlayer(name));
		}

		String msg = String.format(
				_("Added %d player", "Added %d players", players.size()),
				players.size());

		sendMsg(sender, msg);
	}

	private void addPlayer(ICommandSender sender, String name) {
		Map<String, MCPlayer> players = SporzMC.getPlayers();
		players.put(name, new MCPlayer(name));
		sendMsg(sender, String.format(_("Added %s"), name));
	}

	private void removePlayer(ICommandSender sender, String name) {
		MCPlayer p = SporzMC.getPlayers().remove(name);
		if(p == null) {
			sendMsg(sender, _("No player removed"));
		} else {
			sendMsg(sender, _("Player %s removed"));
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> list = new LinkedList<String>();
		if(args.length == 1) {
			if(_("init").startsWith(args[0]))   list.add(_("init"));
			if(_("add").startsWith(args[0]))    list.add(_("add"));
			if(_("remove").startsWith(args[0])) list.add(_("remove"));
		}
		return list;
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if(SporzMC.isStarted())
			return false;
		return isOp(sender.getName()) || sender.canUseCommand(100, "sporz players");
	}
}
