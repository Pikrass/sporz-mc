package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.ElectCaptain;
import net.pikrass.sporz.actions.BlankVoteProhibitedException;

public class ElectCaptainHandler extends ActionHandler
implements CmdClearHandler, CmdElectHandler, CmdDoneHandler {
	private Game game;
	private MCPlayer player;
	private ElectCaptain action;

	public ElectCaptainHandler(Game game, MCPlayer player, ElectCaptain action) {
		this.game = game;
		this.player = player;
		this.action = action;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandElect().register(player, this);
		SporzMC.getCommand().getCommandDone().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player, this);
		SporzMC.getCommand().getCommandElect().unregister(player, this);
		SporzMC.getCommand().getCommandDone().unregister(player, this);
	}

	public void elect(Player p) throws BlankVoteProhibitedException {
		action.choose(player, action.new Vote(p));
	}

	public void done() {
		try {
			action.choose(player, action.new Vote(null));
		} catch (BlankVoteProhibitedException e) { }
	}

	public void clear() {
		action.choose(player, null);
	}
}
