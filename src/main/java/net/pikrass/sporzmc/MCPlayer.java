package net.pikrass.sporzmc;

import net.pikrass.sporz.*;
import net.pikrass.sporz.events.*;
import net.pikrass.sporz.actions.*;

public class MCPlayer extends Player {
	public MCPlayer(String username) {
		super(username);
	}

	public void notifyRound(int num, RoundPeriod period) {
		//TODO
	}
	public void notify(Attribution event) {
		//TODO
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
