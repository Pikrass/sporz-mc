package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Spy;

public class SpyHandler extends ActionHandler
implements CmdClearHandler, CmdSpyHandler, CmdDoneHandler {
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
		SporzMC.getCommand().getCommandDone().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player);
		SporzMC.getCommand().getCommandSpy().unregister(player);
		SporzMC.getCommand().getCommandDone().unregister(player);
	}

	public void spy(Player p) {
		action.choose(player, action.new Do(p));
	}

	public void done() {
		action.choose(player, action.new Do(Player.NOBODY));
	}

	public void clear() {
		action.choose(player, null);
	}
}

