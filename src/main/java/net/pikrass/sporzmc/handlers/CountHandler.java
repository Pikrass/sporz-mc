package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Count;

public class CountHandler extends ActionHandler
implements CmdClearHandler, CmdCountHandler {
	private Game game;
	private MCPlayer player;
	private Count action;

	public CountHandler(Game game, MCPlayer player, Count action) {
		this.game = game;
		this.player = player;
		this.action = action;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandCount().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player, this);
		SporzMC.getCommand().getCommandCount().unregister(player, this);
	}

	public void count(boolean choice) {
		action.choose(player, action.new Do(choice));
	}

	public void clear() {
		action.choose(player, null);
	}
}

