package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Spy;

public class SpyHandler extends ActionHandler
implements CmdClearHandler, CmdSpyHandler {
	private Game game;
	private MCPlayer player;
	private Spy action;

	public SpyHandler(Game game, MCPlayer player, Spy action) {
		this.game = game;
		this.player = player;
		this.action = action;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandSpy().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player, this);
		SporzMC.getCommand().getCommandSpy().unregister(player, this);
	}

	public void spy(Player p) {
		action.choose(player, action.new Do(p));
	}

	public void clear() {
		action.choose(player, null);
	}
}

