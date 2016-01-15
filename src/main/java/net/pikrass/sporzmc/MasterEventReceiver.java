package net.pikrass.sporzmc;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;

import net.pikrass.sporz.*;
import net.pikrass.sporz.events.*;

import java.util.Map;
import java.util.Iterator;

public class MasterEventReceiver implements Master {
	private void sendMasters(String msg) {
		ChatComponentText comp = new ChatComponentText(msg);
		sendMasters(comp);
	}
	private void sendMasters(IChatComponent msg) {
		ServerConfigurationManager manager =
			MinecraftServer.getServer().getConfigurationManager();

		for(String username : SporzMC.getMasters()) {
			Entity master = manager.getPlayerByUsername(username);
			if(master != null) {
				master.addChatMessage(msg);
			}
		}
	}
	private void sendMsg(String msg) {
		ChatComponentText comp = new ChatComponentText(msg);
		sendMsg(comp);
	}
	private void sendMsg(IChatComponent msg) {
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(msg);
	}

	public void notifyRound(int num, RoundPeriod period) {
		SporzMC.setRound(num);
		SporzMC.setRoundPeriod(period);

		if(period == RoundPeriod.DAY)
			sendMsg(String.format(_("========= DAY %d ========="), num));
		else
			sendMsg(String.format(_("======== NIGHT %d ========"), num));
	}

	public void notifyPhase(String name) {
	}

	public void notify(Attribution event) {
		String message = "";

		switch(event.getRole()) {
		case ASTRONAUT:
			if(event.getState() == State.HUMAN) {
				message = _("%s is an astronaut");
			} else {
				message = _("%s is a mutant");
			}
			break;
		case DOCTOR:
			message = _("%s is a doctor");
			break;
		case PSYCHOLOGIST:
			message = _("%s is a psychologist");
			break;
		case GENETICIST:
			message = _("%s is a geneticist");
			break;
		case COMPUTER_ENGINEER:
			message = _("%s is a computer engineer");
			break;
		case HACKER:
			message = _("%s is a hacker");
			break;
		case SPY:
			message = _("%s is a spy");
			break;
		case TRAITOR:
			message = _("%s is a traitor");
			break;
		}

		sendMasters(green(String.format(message, event.getPlayer())));
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
		sendMasters(blue(String.format(_("Mutants paralysed %s"), event.getTarget())));
	}

	public void notify(Mutation event) {
		String message = "";
		boolean red = false;

		switch(event.getResult()) {
			case SUCCESS:
				red = true;
				message = _("%s has been mutated. They are now a mutant.");
				break;
			case FAIL:
				message = _("An attempt was made to mutate %s, but it failed!");
				break;
			case USELESS:
				message = _("%s has been mutated. It was useless.");
				break;
		}

		if(red)
			sendMasters(red(String.format(message, event.getTarget())));
		else
			sendMasters(blue(String.format(message, event.getTarget())));
	}

	public void notify(Murder event) {
		if(event.getOrigin() == Murder.Origin.MUTANTS)
			sendMsg(red(String.format(_("Mutants killed %s"), event.getTarget())));
		else
			sendMsg(red(String.format(_("Doctors killed %s"), event.getTarget())));

		revealPlayer(event.getTarget(), true);
	}

	public void notify(Healing event) {
		String message = "";
		boolean red = false;

		switch(event.getResult()) {
			case SUCCESS:
				red = true;
				message = _("%s has been healed. They are now a human.");
				break;
			case FAIL:
				message = _("An attempt was made to heal %s, but it failed!");
				break;
			case USELESS:
				message = _("%s has been healed. It was useless.");
				break;
		}

		if(red)
			sendMasters(red(String.format(message, event.getTarget())));
		else
			sendMasters(blue(String.format(message, event.getTarget())));
	}

	public void notify(Psychoanalysis event) {
		if(!event.hasResult()) {
			sendMasters(blue(String.format(_("%s didn't psychoanalyse anybody"), event.getOrigin())));
			return;
		}

		String message = "";

		switch(event.getResult()) {
			case HUMAN:
				message = _("%s psychoanalysed %s (human)");
				break;
			case MUTANT:
				message = _("%s psychoanalysed %s (mutant)");
				break;
		}

		sendMasters(blue(String.format(message, event.getOrigin(), event.getTarget())));
	}

	public void notify(Sequencing event) {
		if(!event.hasResult()) {
			sendMasters(blue(String.format(_("%s didn't sequence anybody's genome"), event.getOrigin())));
			return;
		}

		String message = "";

		switch(event.getResult()) {
			case STANDARD:
				message = _("%s sequenced %s (standard)");
				break;
			case RESISTANT:
				message = _("%s sequenced %s (resistant)");
				break;
			case HOST:
				message = _("%s sequenced %s (host)");
				break;
		}

		sendMasters(blue(String.format(message, event.getOrigin(), event.getTarget())));
	}

	public void notify(MutantCount event) {
		if(!event.hasResult()) {
			sendMasters(blue(String.format(_("%s didn't count the mutants"), event.getOrigin())));
			return;
		}

		sendMasters(blue(String.format(_("%s counted the mutants (%d)"), event.getOrigin(), event.getResult())));
	}

	public void notify(Psychoanalysis.Hacked event) {
		String message = "";

		if(!event.hasResult())
			message = _("%s hacked a psychologist who didn't analyse anybody");
		else
			message = _("%s hacked a psychologist who analysed %s");

		sendMasters(blue(String.format(message, event.getHacker(), event.getTarget())));
	}

	public void notify(Sequencing.Hacked event) {
		String message = "";

		if(!event.hasResult())
			message = _("%s hacked a geneticist who didn't analyse anybody");
		else
			message = _("%s hacked a geneticist who analysed %s");

		sendMasters(blue(String.format(message, event.getHacker(), event.getTarget())));
	}

	public void notify(MutantCount.Hacked event) {
		String message = "";

		if(!event.hasResult())
			message = _("%s hacked a computer engineer who didn't count the mutants");
		else
			message = _("%s hacked a computer engineer who counted the mutants");

		sendMasters(blue(String.format(message, event.getHacker())));
	}

	public void notify(SpyReport event) {
		sendMasters(blue(String.format(_("%s spied on %s"), event.getOrigin(), event.getTarget())));
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

		Iterator<Map.Entry<Player, Player>> it2 = event.voteIterator();
		while(it2.hasNext()) {
			Map.Entry<Player, Player> vote = it2.next();
			if(vote.getValue().equals(Player.NOBODY))
				sendMasters(String.format(_("%s voted blank"), vote.getKey()));
			else
				sendMasters(String.format(_("%s voted for %s"), vote.getKey(), vote.getValue()));
		}

		if(event.isDraw()) {
			sendMsg(blue(_("There is a draw. The captain must settle the vote.")));
		} else if(event.getTarget().equals(Player.NOBODY)) {
			sendMsg(blue(_("Nobody is to be killed today")));
		} else {
			sendMsg(blue(String.format(_("You decided to kill %s"), event.getTarget())));
			revealPlayer(event.getTarget(), true);
		}
	}

	public void notify(LynchSettling event) {
		if(event.getTarget().equals(Player.NOBODY)) {
			sendMsg(blue(_("The captain chose not to kill anybody")));
		} else {
			sendMsg(blue(String.format(_("The captain decided to kill %s"), event.getTarget())));
			revealPlayer(event.getTarget(), true);
		}
	}

	public void notify(EndGame event) {
		switch(event.getWinner()) {
			case HUMANS:
				sendMsg(bold(purple(_("=== The game is over! Humans win! ==="))));
				break;
			case MUTANTS:
				sendMsg(bold(purple(_("=== The game is over! Mutants win! ==="))));
				break;
			case DRAW:
				sendMsg(bold(purple(_("=== The game is over! Nobody won... ==="))));
				break;
		}

		switch(event.getReason()) {
			case ANNIHILATION:
				if(event.getWinner() == EndGame.Winner.HUMANS)
					sendMsg(purple(_("Reason: there is no mutant left")));
				else
					sendMsg(purple(_("Reason: there is no human left")));
				break;
			case ASSURED_VICTORY:
				if(event.getWinner() == EndGame.Winner.HUMANS)
					sendMsg(purple(_("Reason: it's now impossible for mutants to win")));
				else
					sendMsg(purple(_("Reason: it's now impossible for humans to win")));
				break;
			case CUSTOM:
				sendMsg(purple(_("Reason: ")+event.getCustomReason()));
				break;
			case NO_REASON:
				break;
		}

		for(MCPlayer player : SporzMC.getPlayers().values()) {
			revealPlayer(player, false);
		}

		SporzMC.endGame();
	}


	private void revealPlayer(Player p, boolean death) {
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

		if(death)
			sendMsg(red(String.format(_("%s was %s, %s, %s"), p, r, g, s)));
		else
			sendMsg(String.format(_("%s was %s, %s, %s"), p, r, g, s));
	}
}
