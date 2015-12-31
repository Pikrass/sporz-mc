package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Psychoanalyse;

public class PsychoanalyseHandler extends ActionHandler
implements CmdClearHandler, CmdPsyHandler {
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
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player, this);
		SporzMC.getCommand().getCommandPsy().unregister(player, this);
	}

	public void psy(Player p) {
		action.choose(player, action.new Do(p));
	}

	public void clear() {
		action.choose(player, null);
	}
}

