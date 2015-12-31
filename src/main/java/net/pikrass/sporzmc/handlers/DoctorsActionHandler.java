package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.DoctorsAction;

public class DoctorsActionHandler extends ActionHandler
implements CmdClearHandler, CmdHealHandler, CmdKillHandler, CmdDoneHandler {
	private Game game;
	private MCPlayer player;
	private DoctorsAction action;
	private int nbHealings;

	public DoctorsActionHandler(Game game, MCPlayer player, DoctorsAction action) {
		this.game = game;
		this.player = player;
		this.action = action;
		this.nbHealings = 0;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandHeal().register(player, this);
		SporzMC.getCommand().getCommandKill().register(player, this);
		SporzMC.getCommand().getCommandDone().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player, this);
		SporzMC.getCommand().getCommandHeal().unregister(player, this);
		SporzMC.getCommand().getCommandKill().unregister(player, this);
		SporzMC.getCommand().getCommandDone().unregister(player, this);
	}

	public void kill(Player p) {
		this.nbHealings = 0;
		action.choose(player, action.new Kill(p));
	}

	public void heal(Player p) {
		this.nbHealings += 1;
		action.choose(player, action.new Heal(p));
	}

	public void done() {
		int availHealings = action.new Heal(Player.NOBODY).getAvailable();
		while(nbHealings < availHealings) {
			this.nbHealings += 1;
			action.choose(player, action.new Heal(Player.NOBODY));
		}
	}

	public void clear() {
		this.nbHealings = 0;
		action.choose(player, null);
	}
}

