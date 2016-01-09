package net.pikrass.sporzmc;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.command.ICommandSender;

import net.pikrass.sporz.*;
import net.pikrass.sporz.events.*;

import java.util.Map;
import java.util.Iterator;

public class MasterEventReceiver implements Master {
	private void sendMsg(String msg) {
		ChatComponentText comp = new ChatComponentText(msg);
		sendMsg(comp);
	}
	private void sendMsg(IChatComponent msg) {
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(msg);
	}

	public void notifyRound(int num, RoundPeriod period) {
		if(period == RoundPeriod.DAY)
			sendMsg(String.format(_("========= DAY %d ========="), num));
		else
			sendMsg(String.format(_("======== NIGHT %d ========"), num));
	}

	public void notifyPhase(String name) {
	}

	public void notify(Attribution event) {
	}

	public void notify(NewCaptain event) {
		sendMsg(blue(_("The election is closed. Results follow:")));
		Iterator<Map.Entry<Player, Player>> it = event.voteIterator();
		while(it.hasNext()) {
			Map.Entry<Player, Player> vote = it.next();
			sendMsg(String.format(_("%s voted for %s"), vote.getKey(), vote.getValue()));
		}
		sendMsg(blue(String.format(_("%s is elected captain!"), event.getWinner())));
	}

	public void notify(Paralysis event) {
	}

	public void notify(Mutation event) {
	}

	public void notify(Murder event) {
		if(event.getOrigin() == Murder.Origin.MUTANTS)
			sendMsg(red(String.format(_("Mutants killed %s"), event.getTarget())));
		else
			sendMsg(red(String.format(_("Doctors killed %s"), event.getTarget())));

		revealPlayer(event.getTarget());
	}

	public void notify(Healing event) {
	}

	public void notify(Psychoanalysis event) {
	}

	public void notify(Sequencing event) {
	}

	public void notify(MutantCount event) {
	}

	public void notify(Psychoanalysis.Hacked event) {
	}

	public void notify(Sequencing.Hacked event) {
	}

	public void notify(MutantCount.Hacked event) {
	}

	public void notify(SpyReport event) {
	}

	public void notify(Lynching event) {
		sendMsg(blue(_("Votes are closed. Results follow:")));
		Iterator<Map.Entry<Player, Integer>> it = event.countIterator();
		while(it.hasNext()) {
			Map.Entry<Player, Integer> vote = it.next();

			if(vote.getKey().equals(Player.NOBODY)) {
				sendMsg(String.format(_("%d astronaut voted blank",
								"%d astronauts voted blank", vote.getValue()),
							vote.getValue()));
			} else {
				sendMsg(String.format(_("%d astronaut voted for %s",
								"%d astronauts voted for %s", vote.getValue()),
							vote.getValue(), vote.getKey()));
			}
		}

		if(event.isDraw()) {
			sendMsg(blue(_("There is a draw. The captain must settle the vote.")));
		} else if(event.getTarget().equals(Player.NOBODY)) {
			sendMsg(blue(_("Nobody is to be killed today")));
		} else {
			sendMsg(blue(String.format(_("You decided to kill %s"), event.getTarget())));
			revealPlayer(event.getTarget());
		}
	}

	public void notify(LynchSettling event) {
		if(event.getTarget().equals(Player.NOBODY)) {
			sendMsg(blue(_("The captain chose not to kill anybody")));
		} else {
			sendMsg(blue(String.format(_("The captain decided to kill %s"), event.getTarget())));
			revealPlayer(event.getTarget());
		}
	}


	private void revealPlayer(Player p) {
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

		switch(p.getGenome()) {
			case RESISTANT: g = _("resistant"); break;
			case STANDARD:  g = _("standard"); break;
			case HOST:      g = _("host"); break;
		}

		if(p.getState() == State.HUMAN)
			s = _("human");
		else
			s = _("mutant");

		sendMsg(red(String.format(_("%s was %s, %s, %s"), p, r, g, s)));
	}
}
