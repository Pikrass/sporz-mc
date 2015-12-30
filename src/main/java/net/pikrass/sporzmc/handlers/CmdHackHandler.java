package net.pikrass.sporzmc.handlers;

import net.pikrass.sporz.Player;
import net.pikrass.sporz.actions.InvalidChoiceException;

import java.util.Set;

public interface CmdHackHandler {
	public void hack(String choice) throws InvalidChoiceException;
	public Set<String> getHackableRoles();
}
