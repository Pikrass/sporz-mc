package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;
import net.pikrass.sporz.events.EndGame;

import java.util.List;
import java.util.LinkedList;

public class CommandEnd extends SporzSubcommand
{
	@Override
	public String getName() {
		return _("end");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("end [humans|mutants|draw] [reason]");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("end [humans|mutants|draw] [reason]");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) {
		EndGame.Winner winner = EndGame.Winner.DRAW;
		String reason = null;

		if(params.length > 0) {
			if(params[0].equals(_("humans"))) {
				winner = EndGame.Winner.HUMANS;
			} else if(params[0].equals(_("mutants"))) {
				winner = EndGame.Winner.MUTANTS;
			} else if(params[0].equals(_("draw"))) {
				winner = EndGame.Winner.DRAW;
			} else {
				printShortUsage(sender);
				return;
			}
		}

		if(params.length > 1) {
			StringBuffer buf = new StringBuffer();
			buf.append(params[1]);
			for(int i=2 ; i<params.length ; i++) {
				buf.append(" ");
				buf.append(params[i]);
			}

			reason = buf.toString();
		}

		EndGame result = null;
		if(reason == null)
			result = new EndGame(winner);
		else
			result = new EndGame(winner, reason);

		SporzMC.getGame().end(result);
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> list = new LinkedList<String>();
		if(args.length == 1) {
			if(_("humans").startsWith(args[0]))
				list.add(_("humans"));
			if(_("mutants").startsWith(args[0]))
				list.add(_("mutants"));
			if(_("draw").startsWith(args[0]))
				list.add(_("draw"));
		}
		return list;
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		if(!SporzMC.isStarted())
			return false;
		return SporzMC.isMaster(sender) || isOp(sender.getName())
			|| sender.canUseCommand(100, "sporz end");
	}
}

