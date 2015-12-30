package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Psychoanalyse;

public class PsychoanalyseHandler extends ActionHandler
implements CmdClearHandler, CmdPsyHandler, CmdDoneHandler {
	private Game game;
	private MCPlayer player;
	private Psychoanalyse action;

	public PsychoanalyseHandler(Game game, MCPlayer player, Psychoanalyse action) {
		this.game = game;
		this.player = player;
		this.action = action;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandPsy().register(player, this);
		SporzMC.getCommand().getCommandDone().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player);
		SporzMC.getCommand().getCommandPsy().unregister(player);
		SporzMC.getCommand().getCommandDone().unregister(player);
	}

	public void psy(Player p) {
		action.choose(player, action.new Do(p));
	}

	public void done() {
		action.choose(player, action.new Do(Player.NOBODY));
	}

	public void clear() {
		action.choose(player, null);
	}
}

