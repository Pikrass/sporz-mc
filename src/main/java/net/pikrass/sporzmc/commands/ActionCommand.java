package net.pikrass.sporzmc.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandException;
import net.minecraft.util.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import net.pikrass.sporzmc.*;

public abstract class ActionCommand<Handler> extends SporzSubcommand {
	private Map<MCPlayer, Set<Handler>> handlers;

	public ActionCommand() {
		super();
		this.handlers = new HashMap<MCPlayer, Set<Handler>>();
	}

	public void register(MCPlayer player, Handler handler) {
		Set<Handler> set = handlers.get(player);
		if(set == null) {
			set = new HashSet<Handler>();
			handlers.put(player, set);
		}
		set.add(handler);
	}

	public void unregister(MCPlayer player, Handler handler) {
		Set<Handler> set = handlers.get(player);
		if(set == null)
			return;

		set.remove(handler);

		if(set.isEmpty())
			handlers.remove(player);
	}

	private Handler getHandler(ICommandSender sender) {
		MCPlayer player = SporzMC.getPlayers().get(sender.getName());
		for(Handler handler : handlers.get(player))
			return handler;
		return null;
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return getCommandShortUsage(sender, getHandler(sender));
	}

	public String getCommandShortUsage(ICommandSender sender, Handler handler) {
		return "";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return getCommandUsage(sender, getHandler(sender));
	}

	public String getCommandUsage(ICommandSender sender, Handler handler) {
		return "";
	}

	@Override
	public void execute(ICommandSender sender, String[] params) throws CommandException {
		preexecute(sender, params);

		MCPlayer player = SporzMC.getPlayers().get(sender.getName());
		for(Handler handler : handlers.get(player))
			execute(sender, handler, params);
	}

	protected void preexecute(ICommandSender sender, String[] params) {
	}
	protected abstract void execute(ICommandSender sender, Handler handler, String[] params);

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return addTabCompletionOptions(sender, args, pos, getHandler(sender));
	}

	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos, Handler handler) {
		return null;
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		MCPlayer player = SporzMC.getPlayers().get(sender.getName());
		if(player == null)
			return false;
		return handlers.containsKey(player);
	}
}
