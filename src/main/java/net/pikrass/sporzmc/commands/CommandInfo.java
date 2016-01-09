package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.*;

import java.util.List;
import java.util.LinkedList;

public class CommandInfo extends SporzSubcommand
{
	@Override
	public String getName() {
		return _("info");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("info");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return _("info");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) {
		int round = SporzMC.getRound();
		RoundPeriod period = SporzMC.getRoundPeriod();

		if(period == RoundPeriod.DAY)
			sendMsg(sender, String.format(_("========= DAY %d ========="), round));
		else
			sendMsg(sender, String.format(_("======== NIGHT %d ========"), round));

		for(MCPlayer player : SporzMC.getPlayers().values()) {
			String infos = "";

			if(!player.isAlive() || SporzMC.isMaster(sender)) {
				infos = getPlayerInfo(player, true);
			} else if(sender.getName().equals(player.getName())) {
				infos = getPlayerInfo(player, false);
			}

			if(player.equals(SporzMC.getGame().getCaptain())) {
				if(infos.equals(""))
					infos = _("[captain]");
				else
					infos += _(", [captain]");
			}


			String msg = null;

			if(infos.equals(""))
				msg = player.getName();
			else
				msg = String.format(_("%s: %s"), player.getName(), infos);

			if(player.isAlive())
				sendMsg(sender, msg);
			else
				sendMsg(sender, strike(msg));
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		return new LinkedList<String>();
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return SporzMC.isStarted();
	}


	private String getPlayerInfo(Player p, boolean genome) {
		String r = "", g = "", s = "";

		switch(p.getRole()) {
			case ASTRONAUT:         r = _("astronaut"); break;
			case DOCTOR:            r = _("doctor"); break;
			case PSYCHOLOGIST:      r = _("psychologist"); break;
			case GENETICIST:        r = _("geneticist"); break;
			case COMPUTER_ENGINEER: r = _("computer engineer"); break;
			case HACKER:            r = _("hacker"); break;
			case SPY:               r = _("spy"); break;
			case TRAITOR:           r = _("traitor"); break;
		}

		if(genome) {
			switch(p.getGenome()) {
				case RESISTANT: g = _("resistant"); break;
				case STANDARD:  g = _("standard"); break;
				case HOST:      g = _("host"); break;
			}
		}

		if(p.getState() == State.HUMAN)
			s = _("human");
		else
			s = _("mutant");

		if(genome)
			return String.format(_("%s, %s, %s"), r, g, s);
		else
			return String.format(_("%s, %s"), r, s);
	}
}
