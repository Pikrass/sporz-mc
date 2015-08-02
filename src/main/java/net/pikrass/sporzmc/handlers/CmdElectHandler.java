package net.pikrass.sporzmc.handlers;

import net.pikrass.sporz.Player;
import net.pikrass.sporz.actions.BlankVoteProhibitedException;

public interface CmdElectHandler {
	public void elect(Player p) throws BlankVoteProhibitedException;
}
