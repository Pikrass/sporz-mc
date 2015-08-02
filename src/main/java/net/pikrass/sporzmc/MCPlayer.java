package net.pikrass.sporzmc;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.green;

import net.pikrass.sporz.*;
import net.pikrass.sporz.events.*;
import net.pikrass.sporz.actions.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;

public class MCPlayer extends Player {
	public MCPlayer(String username) {
		super(username);
	}

	protected Entity getEntity() {
		ServerConfigurationManager manager =
			MinecraftServer.getServer().getConfigurationManager();
		return manager.getPlayerByUsername(getName());
	}

	public ICommandSender getCommandSender() {
		return getEntity();
	}

	protected void sendMsg(String message) {
		Entity entity = getEntity();
		if(entity == null)
			return;

		net.pikrass.sporzmc.util.MinecraftHelper.sendMsg(entity, message);
	}

	public void notifyRound(int num, RoundPeriod period) {
		if(period == RoundPeriod.DAY)
			sendMsg(String.format(_("========= DAY %d ========="), num));
		else
			sendMsg(String.format(_("======== NIGHT %d ========"), num));
	}
	public void notify(Attribution event) {
		switch(event.getRole()) {
		case ASTRONAUT:
			if(event.getState() == State.HUMAN) {
				sendMsg(green(_("You are an astronaut")));
				sendMsg(_("You don't have any particular power"));
			} else {
				sendMsg(green(_("You are a mutant")));
				sendMsg(_("You don't have any particular power, but you can't be healed"));
			}
			break;
		case DOCTOR:
			sendMsg(green(_("You are a doctor")));
			sendMsg(_("Each night, if you're not a mutant, you can heal or kill players"));

			StringBuffer names = new StringBuffer();
			int nb = event.getGroup().size();
			for(Player p : event.getGroup()) {
				if(!p.equals(this))
					names.append(p.getName()+", ");
			}
			if(nb > 0) {
				String group = names.substring(0, names.length()-2);
				sendMsg(String.format(_("The other doctor is %s", "The other doctors are %s", nb), group));
			}

			break;
		case PSYCHOLOGIST:
			sendMsg(green(_("You are a psychologist")));
			sendMsg(_("Each night you can see the state of another player (human or mutant)"));
			break;
		case GENETICIST:
			sendMsg(green(_("You are a geneticist")));
			sendMsg(_("Each night you can see the genome of another player (standard, host or resistant)"));
			break;
		case COMPUTER_ENGINEER:
			sendMsg(green(_("You are a computer engineer")));
			sendMsg(_("Each night you can see the number of mutants on board"));
			break;
		case HACKER:
			sendMsg(green(_("You are a hacker")));
			sendMsg(_("Each night you can see what a psychologist, geneticist or computer engineer saw"));
			break;
		case SPY:
			sendMsg(green(_("You are a spy")));
			sendMsg(_("Each night you can see what happened to another player"));
			break;
		case TRAITOR:
			sendMsg(green(_("You are a traitor")));
			sendMsg(_("Your goal is always to make the mutants win, regardless of your own state"));
			break;
		}
	}
	public void notify(NewCaptain event) {
		//TODO
	}
	public void notifyOrigin(Paralysis event) {
		//TODO
	}
	public void notifyTarget(Paralysis event) {
		//TODO
	}
	public void notifyOrigin(Mutation.NoResult event) {
		//TODO
	}
	public void notifyTarget(Mutation event) {
		//TODO
	}
	public void notify(Murder event) {
		//TODO
	}
	public void notifyOrigin(Healing.NoResult event) {
		//TODO
	}
	public void notifyTarget(Healing event) {
		//TODO
	}
	public void notify(Psychoanalysis event) {
		//TODO
	}
	public void notify(Sequencing event) {
		//TODO
	}
	public void notify(MutantCount event) {
		//TODO
	}
	public void notify(Psychoanalysis.Hacked event) {
		//TODO
	}
	public void notify(Sequencing.Hacked event) {
		//TODO
	}
	public void notify(MutantCount.Hacked event) {
		//TODO
	}
	public void notify(SpyReport event) {
		//TODO
	}
	public void notify(Lynching.Anonymous event) {
		//TODO
	}
	public void notify(LynchSettling event) {
		//TODO
	}

	public void ask(Game game, ElectCaptain action) {
		//TODO
	}
	public void ask(Game game, MutantsActions action) {
		//TODO
	}
	public void ask(Game game, DoctorsAction action) {
		//TODO
	}
	public void ask(Game game, Psychoanalyse action) {
		//TODO
	}
	public void ask(Game game, Sequence action) {
		//TODO
	}
	public void ask(Game game, Count action) {
		//TODO
	}
	public void ask(Game game, Hack action) {
		//TODO
	}
	public void ask(Game game, Spy action) {
		//TODO
	}
	public void ask(Game game, Lynch action) {
		//TODO
	}
	public void ask(Game game, SettleLynch action) {
		//TODO
	}

	public void stopAsking(ElectCaptain action) {
		//TODO
	}
	public void stopAsking(MutantsActions action) {
		//TODO
	}
	public void stopAsking(DoctorsAction action) {
		//TODO
	}
	public void stopAsking(Psychoanalyse action) {
		//TODO
	}
	public void stopAsking(Sequence action) {
		//TODO
	}
	public void stopAsking(Count action) {
		//TODO
	}
	public void stopAsking(Hack action) {
		//TODO
	}
	public void stopAsking(Spy action) {
		//TODO
	}
	public void stopAsking(Lynch action) {
		//TODO
	}
	public void stopAsking(SettleLynch action) {
		//TODO
	}
}
