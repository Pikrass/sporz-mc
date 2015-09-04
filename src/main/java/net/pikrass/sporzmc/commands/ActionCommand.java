package net.pikrass.sporzmc.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandException;

import java.util.HashMap;
import java.util.Map;

import net.pikrass.sporzmc.*;

public abstract class ActionCommand<Handler> extends SporzSubcommand {
	private Map<MCPlayer, Handler> handlers;

	public ActionCommand() {
		super();
		this.handlers = new HashMap<MCPlayer, Handler>();
	}

	public void register(MCPlayer player, Handler handler) {
		handlers.put(player, handler);
	}

	public void unregister(MCPlayer player) {
		handlers.remove(player);
	}

	@Override
	public void execute(ICommandSender sender, String[] params) throws CommandException {
		MCPlayer player = SporzMC.getPlayers().get(sender.getName());
		execute(sender, handlers.get(player), params);
	}

	protected abstract void execute(ICommandSender sender, Handler handler, String[] params);

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		MCPlayer player = SporzMC.getPlayers().get(sender.getName());
		if(player == null)
			return false;
		return handlers.containsKey(player);
	}
}