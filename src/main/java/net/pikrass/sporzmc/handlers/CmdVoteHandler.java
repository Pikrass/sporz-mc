package net.pikrass.sporzmc.handlers;

import net.pikrass.sporz.Player;
import net.pikrass.sporz.actions.InvalidChoiceException;

import java.util.Set;

public interface CmdVoteHandler {
	public void vote(Player p) throws InvalidChoiceException;
	public Set<Player> getVoteChoices();
}
