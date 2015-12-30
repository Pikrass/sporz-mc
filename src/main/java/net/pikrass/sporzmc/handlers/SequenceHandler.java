package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Sequence;

public class SequenceHandler extends ActionHandler
implements CmdClearHandler, CmdGenetHandler, CmdDoneHandler {
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
		SporzMC.getCommand().getCommandDone().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player);
		SporzMC.getCommand().getCommandGenet().unregister(player);
		SporzMC.getCommand().getCommandDone().unregister(player);
	}

	public void genet(Player p) {
		action.choose(player, action.new Do(p));
	}

	public void done() {
		action.choose(player, action.new Do(Player.NOBODY));
	}

	public void clear() {
		action.choose(player, null);
	}
}

