package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.SettleLynch;
import net.pikrass.sporz.actions.InvalidChoiceException;

import java.util.Set;
import java.util.HashSet;

public class SettleLynchHandler extends ActionHandler
implements CmdClearHandler, CmdVoteHandler {
	private Game game;
	private MCPlayer player;
	private SettleLynch action;

	public SettleLynchHandler(Game game, MCPlayer player, SettleLynch action) {
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

	public void vote(Player p) throws InvalidChoiceException {
		action.choose(player, action.new Do(p));
	}

	public void clear() {
		action.choose(player, null);
	}

	public Set<Player> getVoteChoices() {
		return action.getChoices();
	}
}
