package net.pikrass.sporzmc;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.red;
import static net.pikrass.sporzmc.util.MinecraftHelper.green;
import static net.pikrass.sporzmc.util.MinecraftHelper.blue;
import static net.pikrass.sporzmc.util.MinecraftHelper.gold;

import net.pikrass.sporz.*;
import net.pikrass.sporz.events.*;
import net.pikrass.sporz.actions.*;

import net.pikrass.sporzmc.handlers.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.WorldSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.util.IChatComponent;

import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;

public class MCPlayer extends Player {
	private Map<Action, ActionHandler> handlers;
	private Vec3 room;

	public MCPlayer(String username) {
		super(username);
		this.handlers = new HashMap<Action, ActionHandler>();
		this.room = null;
	}

	public void assignRoom(Vec3 room) {
		this.room = room;
	}

	protected Entity getEntity() {
		ServerConfigurationManager manager =
			MinecraftServer.getServer().getConfigurationManager();
		return manager.getPlayerByUsername(getName());
	}


	@Override
	public void kill() {
		super.kill();
		Entity entity = getEntity();
		entity.onKillCommand();
		if(entity instanceof EntityPlayerMP)
			((EntityPlayerMP)entity).setGameType(WorldSettings.GameType.SPECTATOR);
	}

	private void teleportRoom() {
		if(this.room != null) {
			teleport(room);
		}
	}

	private void teleportHub() {
		teleportZone(SporzMC.getHub());
	}

	private void teleportRoomOrHub() {
		if(this.room != null) {
			teleport(room);
		} else {
			teleportZone(SporzMC.getHub());
		}
	}

	private void teleportZone(AxisAlignedBB zone) {
		Vec3 pos = new Vec3(
				zone.minX + (zone.maxX - zone.minX) * Math.random(),
				zone.minY + (zone.maxY - zone.minY) * Math.random(),
				zone.minZ + (zone.maxZ - zone.minZ) * Math.random()
				);
		teleport(pos);
	}

	private void teleport(Vec3 pos) {
		Entity entity = getEntity();
		entity.setLocationAndAngles(pos.xCoord, pos.yCoord, pos.zCoord,
				entity.rotationYaw, entity.rotationPitch);
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
	protected void sendMsg(IChatComponent message) {
		Entity entity = getEntity();
		if(entity == null)
			return;

		net.pikrass.sporzmc.util.MinecraftHelper.sendMsg(entity, message);
	}

	public void notifyRound(int num, RoundPeriod period) {
		if(period == RoundPeriod.DAY)
			teleportHub();
		else
			teleportRoom();
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
	}
	public void notifyOrigin(Paralysis event) {
		sendMsg(blue(String.format(_("You paralysed %s"), event.getTarget())));
	}
	public void notifyTarget(Paralysis event) {
		sendMsg(red(_("You've been paralysed")));
	}
	public void notifyOrigin(Mutation.NoResult event) {
		sendMsg(blue(String.format(_("You try to mutate %s"), event.getTarget())));
	}
	public void notifyTarget(Mutation event) {
		switch(event.getResult()) {
			case SUCCESS:
				sendMsg(red(_("You've been mutated. You are now a mutant.")));
				break;
			case FAIL:
				sendMsg(blue(_("An attempt was made to mutate you, but it failed!")));
				break;
			case USELESS:
				sendMsg(blue(_("You've been mutated. It was useless.")));
				break;
		}
	}
	public void notify(Murder event) {
	}
	public void notifyOrigin(Healing.NoResult event) {
		sendMsg(blue(String.format(_("You try to heal %s"), event.getTarget())));
	}
	public void notifyTarget(Healing event) {
		switch(event.getResult()) {
			case SUCCESS:
				sendMsg(red(_("You've been healed. You are now a human.")));
				break;
			case FAIL:
				sendMsg(blue(_("An attempt was made to heal you, but it failed!")));
				break;
			case USELESS:
				sendMsg(blue(_("You've been healed. It was useless.")));
				break;
		}
	}
	public void notify(Psychoanalysis event) {
		if(!event.hasResult()) {
			sendMsg(blue(_("You didn't psychoanalyse anybody")));
			return;
		}

		switch(event.getResult()) {
			case HUMAN:
				sendMsg(blue(String.format(_("%s is human"), event.getTarget())));
				break;
			case MUTANT:
				sendMsg(blue(String.format(_("%s is mutant"), event.getTarget())));
				break;
		}
	}
	public void notify(Sequencing event) {
		if(!event.hasResult()) {
			sendMsg(blue(_("You didn't sequence anybody's genome")));
			return;
		}

		switch(event.getResult()) {
			case STANDARD:
				sendMsg(blue(String.format(_("%s is standard"), event.getTarget())));
				break;
			case RESISTANT:
				sendMsg(blue(String.format(_("%s is resistant"), event.getTarget())));
				break;
			case HOST:
				sendMsg(blue(String.format(_("%s is host"), event.getTarget())));
				break;
		}
	}
	public void notify(MutantCount event) {
		if(!event.hasResult()) {
			sendMsg(blue(_("You didn't count the mutants")));
			return;
		}

		sendMsg(blue(String.format(
						_("There is %d mutant", "There are %d mutants", event.getResult()),
						event.getResult())));
	}
	public void notify(Psychoanalysis.Hacked event) {
		if(!event.hasResult()) {
			sendMsg(blue(_("The psychologist had no information tonight")));
			return;
		}

		switch(event.getResult()) {
			case HUMAN:
				sendMsg(blue(String.format(_("The psychologist concluded that %s is human"), event.getTarget())));
				break;
			case MUTANT:
				sendMsg(blue(String.format(_("The psychologist concluded that %s is mutant"), event.getTarget())));
				break;
		}
	}
	public void notify(Sequencing.Hacked event) {
		if(!event.hasResult()) {
			sendMsg(blue(_("The geneticist had no information tonight")));
			return;
		}

		switch(event.getResult()) {
			case STANDARD:
				sendMsg(blue(String.format(_("The geneticist concluded that %s is standard"), event.getTarget())));
				break;
			case RESISTANT:
				sendMsg(blue(String.format(_("The geneticist concluded that %s is resistant"), event.getTarget())));
				break;
			case HOST:
				sendMsg(blue(String.format(_("The geneticist concluded that %s is host"), event.getTarget())));
				break;
		}
	}
	public void notify(MutantCount.Hacked event) {
		if(!event.hasResult()) {
			sendMsg(blue(_("The computer engineer had no information tonight")));
			return;
		}

		sendMsg(blue(String.format(_("The computer engineer counted %d mutant",
							"The computer engineer counted %d mutants",
							event.getResult()),
						event.getResult())));
	}
	public void notify(SpyReport event) {
		SortedSet<SpyReport.Line> lines = event.getResult();
		if(lines.isEmpty()) {
			sendMsg(blue(String.format(_("Nothing happened to %s tonight"), event.getTarget())));
			return;
		}

		StringBuffer msg = new StringBuffer(String.format(_("Tonight, %s was"), event.getTarget()));
		msg.append(" ");

		for(SpyReport.Line line : lines) {
			int id;
			switch(line.getType()) {
				case MUTATION:
					msg.append(_("mutated"));
					break;
				case PARALYSIS:
					msg.append(_("paralysed"));
					break;
				case HEALING:
					msg.append(_("healed"));
					break;
				case PSYCHOANALYSIS:
					id = Integer.parseInt(line.getName().substring(1));
					msg.append(String.format(_("psychoanalysed by psy n°%d"), id));
					break;
				case SEQUENCING:
					id = Integer.parseInt(line.getName().substring(1));
					msg.append(String.format(_("sequenced by genet n°%d"), id));
					break;
			}
			msg.append(", ");
		}

		msg.delete(msg.length()-2, msg.length());
		sendMsg(blue(msg.toString()));
	}
	public void notify(Lynching.Anonymous event) {
	}
	public void notify(LynchSettling event) {
	}

	public void ask(Game game, ElectCaptain action) {
		ElectCaptainHandler handler = new ElectCaptainHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("We are in need of a captain! Vote with the /sporz elect command")));
	}
	public void ask(Game game, MutantsActions action) {
		MutantsActionsHandler handler = new MutantsActionsHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("Mutants, you may now mutate or kill, in addition to paralyse, players")));
		teleportZone(SporzMC.getMutantsRoom());
	}
	public void ask(Game game, DoctorsAction action) {
		DoctorsActionHandler handler = new DoctorsActionHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("Doctors, you can now heal or kill players")));
		teleportZone(SporzMC.getDoctorsRoom());
	}
	public void ask(Game game, Psychoanalyse action) {
		PsychoanalyseHandler handler = new PsychoanalyseHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("Choose someone to psychoanalyse")));
	}
	public void ask(Game game, Sequence action) {
		SequenceHandler handler = new SequenceHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("Choose someone whose genome to sequence")));
	}
	public void ask(Game game, Count action) {
		CountHandler handler = new CountHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("Choose whether you want to count the mutants")));
	}
	public void ask(Game game, Hack action) {
		HackHandler handler = new HackHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("Choose a role to hack")));
	}
	public void ask(Game game, Spy action) {
		SpyHandler handler = new SpyHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("Choose a player to spy on")));
	}
	public void ask(Game game, Lynch action) {
		LynchHandler handler = new LynchHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("Talk with the other astronauts, and vote to kill (or not) someone!")));
	}
	public void ask(Game game, SettleLynch action) {
		SettleLynchHandler handler = new SettleLynchHandler(game, this, action);
		handlers.put(action, handler);
		handler.start();

		sendMsg(gold(_("As captain of this ship, vote again to break the tie")));
	}


	private void genericStopAsking(Action action) {
		ActionHandler handler = handlers.get(action);
		if(handler != null) {
			handler.stop();
			handlers.remove(action);
		}
	}

	public void stopAsking(ElectCaptain action) {
		genericStopAsking(action);
	}
	public void stopAsking(MutantsActions action) {
		genericStopAsking(action);
		teleportRoomOrHub();
	}
	public void stopAsking(DoctorsAction action) {
		genericStopAsking(action);
		teleportRoomOrHub();
	}
	public void stopAsking(Psychoanalyse action) {
		genericStopAsking(action);
	}
	public void stopAsking(Sequence action) {
		genericStopAsking(action);
	}
	public void stopAsking(Count action) {
		genericStopAsking(action);
	}
	public void stopAsking(Hack action) {
		genericStopAsking(action);
	}
	public void stopAsking(Spy action) {
		genericStopAsking(action);
	}
	public void stopAsking(Lynch action) {
		genericStopAsking(action);
	}
	public void stopAsking(SettleLynch action) {
		genericStopAsking(action);
	}
}
