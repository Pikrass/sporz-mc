package net.pikrass.sporzmc.handlers;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.actions.Hack;
import net.pikrass.sporz.actions.InvalidChoiceException;

import java.util.Set;

public class HackHandler extends ActionHandler
implements CmdClearHandler, CmdHackHandler {
	private Game game;
	private MCPlayer player;
	private Hack action;

	public HackHandler(Game game, MCPlayer player, Hack action) {
		this.game = game;
		this.player = player;
		this.action = action;
	}

	public void start() {
		SporzMC.getCommand().getCommandClear().register(player, this);
		SporzMC.getCommand().getCommandHack().register(player, this);
	}

	public void stop() {
		SporzMC.getCommand().getCommandClear().unregister(player);
		SporzMC.getCommand().getCommandHack().unregister(player);
	}

	public void hack(String choice) throws InvalidChoiceException {
		action.choose(player, action.new Do(choice));
	}

	public void clear() {
		action.choose(player, null);
	}


	public Set<String> getHackableRoles() {
		return action.getPossibleChoices();
	}
}

