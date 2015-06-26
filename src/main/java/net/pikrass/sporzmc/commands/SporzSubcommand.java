package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public abstract class SporzSubcommand extends CommandBase {
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return getCommandShortUsage(sender);
	}

	public abstract String getCommandShortUsage(ICommandSender sender);

	public void printUsage(ICommandSender recv) {
		StringBuffer buf = new StringBuffer();
		String usage = getCommandUsage(recv);

		for(String line : usage.split("\n")) {
			buf.append(_("/sporz ")+line+"\n");
		}

		sendMsg(recv, buf.substring(0, buf.length() - 1));
	}

	public void printShortUsage(ICommandSender recv) {
		sendMsg(recv, red(_("Usage: /sporz ")+getCommandShortUsage(recv)));
	}

	public void printSummary(ICommandSender recv) {
		sendMsg(recv, _("/sporz ")+getCommandShortUsage(recv));
	}
}
