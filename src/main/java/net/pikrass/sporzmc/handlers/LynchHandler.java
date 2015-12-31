package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Lynch;

public class LynchHandler extends ActionHandler
implements CmdClearHandler, CmdVoteHandler {
	private Game game;
	private MCPlayer player;
	private Lynch action;

	public LynchHandler(Game game, MCPlayer player, Lynch action) {
		this.game = game;
		this.player = player;
		this.action = action;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandVote().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player, this);
		SporzMC.getCommand().getCommandVote().unregister(player, this);
	}

	public void vote(Player p) {
		action.choose(player, action.new Vote(p));
	}

	public void clear() {
		action.choose(player, null);
	}
}

