package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Sequence;

public class SequenceHandler extends ActionHandler
implements CmdClearHandler, CmdGenetHandler {
	private Game game;
	private MCPlayer player;
	private Sequence action;

	public SequenceHandler(Game game, MCPlayer player, Sequence action) {
		this.game = game;
		this.player = player;
		this.action = action;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandGenet().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player, this);
		SporzMC.getCommand().getCommandGenet().unregister(player, this);
	}

	public void genet(Player p) {
		action.choose(player, action.new Do(p));
	}

	public void clear() {
		action.choose(player, null);
	}
}

