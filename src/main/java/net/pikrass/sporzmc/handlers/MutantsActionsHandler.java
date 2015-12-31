package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.MutantsActions;

public class MutantsActionsHandler extends ActionHandler
implements CmdClearHandler, CmdKillHandler, CmdMutateHandler,
					  CmdParalyseHandler, CmdDoneHandler {
	private Game game;
	private MCPlayer player;
	private MutantsActions action;
	private boolean paralyse, mutateOrKill;

	public MutantsActionsHandler(Game game, MCPlayer player, MutantsActions action) {
		this.game = game;
		this.player = player;
		this.action = action;
		this.paralyse = false;
		this.mutateOrKill = false;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandKill().register(player, this);
		SporzMC.getCommand().getCommandMutate().register(player, this);
		SporzMC.getCommand().getCommandParalyse().register(player, this);
		SporzMC.getCommand().getCommandDone().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player, this);
		SporzMC.getCommand().getCommandKill().unregister(player, this);
		SporzMC.getCommand().getCommandMutate().unregister(player, this);
		SporzMC.getCommand().getCommandParalyse().unregister(player, this);
		SporzMC.getCommand().getCommandDone().unregister(player, this);
	}

	public void kill(Player p) {
		this.mutateOrKill = true;
		action.choose(player, action.new Kill(p));
	}

	public void mutate(Player p) {
		this.mutateOrKill = true;
		action.choose(player, action.new Mutate(p));
	}

	public void paralyse(Player p) {
		this.paralyse = true;
		action.choose(player, action.new Paralyse(p));
	}

	public void done() {
		if(!this.paralyse) {
			this.paralyse = true;
			action.choose(player, action.new Paralyse(Player.NOBODY));
		}
		if(!this.mutateOrKill) {
			this.mutateOrKill = true;
			action.choose(player, action.new Mutate(Player.NOBODY));
		}
	}

	public void clear() {
		this.paralyse = false;
		this.mutateOrKill = false;
		action.choose(player, null);
	}
}
