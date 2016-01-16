package net.pikrass.sporzmc;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.InstanceFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.common.config.Configuration;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import net.pikrass.sporzmc.commands.CommandSporz;
import net.pikrass.sporzmc.util.I18n;

import net.pikrass.sporz.Game;
import net.pikrass.sporz.CustomRules;
import net.pikrass.sporz.RoundPeriod;
import net.pikrass.sporz.events.EndGame;

import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

@Mod(
	modid = "Sporz MC",
	useMetadata = true,
	serverSideOnly = true,
	acceptableRemoteVersions = "*"
)

public class SporzMC
{
	private static SporzMC instance = null;

	private CommandSporz command;
	private Configuration config;
	private Game game;
	private Map<String, MCPlayer> players;
	private Set<String> masters;
	private CustomRules rules;
	private boolean started;
	private int round;
	private RoundPeriod period;
	private String phase;

	@InstanceFactory
	public static SporzMC getInstance() {
		if(instance == null)
			instance = new SporzMC();
		return instance;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		this.config = new Configuration(event.getSuggestedConfigurationFile());
		this.config.load();
		I18n.init(Locale.ENGLISH);
		FMLCommonHandler.instance().bus().register(this);
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		command = new CommandSporz();
		players = new HashMap<String, MCPlayer>();
		masters = new HashSet<String>();
		rules = new CustomRules();
		started = false;
		event.registerServerCommand(command);
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		if(!started)
			return;

		MCPlayer player = players.get(event.player.getName());
		if(player == null)
			return;

		player.reconnect(phase);
	}


	public static CommandSporz getCommand() {
		return instance.command;
	}

	public static void startGame() {
		instance.started = true;
		instance.game.start();
	}

	public static Game getGame() {
		return instance.game;
	}

	public static Game initGame() {
		if(instance.game != null)
			instance.game.end(new EndGame(EndGame.Winner.DRAW));
		instance.started = false;
		instance.game = new Game();
		instance.game.setMaster(new MasterEventReceiver());
		return instance.game;
	}

	public static void endGame() {
		instance.game = null;
		instance.started = false;
	}

	public static boolean isStarted() {
		return instance.started;
	}

	public static Map<String, MCPlayer> getPlayers() {
		return instance.players;
	}

	public static void resetMasters() {
		instance.masters.clear();
	}

	public static void addMaster(ICommandSender sender) {
		instance.masters.add(sender.getName());
	}

	public static boolean isMaster(ICommandSender sender) {
		return instance.masters.contains(sender.getName());
	}

	public static Set<String> getMasters() {
		return instance.masters;
	}

	public static void useCurrentPlayers() {
		if(instance.game == null)
			return;
		for(MCPlayer player : instance.players.values())
			instance.game.addPlayer(player);
	}

	public static CustomRules getRules() {
		return instance.rules;
	}

	public static void setRound(int num) {
		instance.round = num;
	}

	public static void setRoundPeriod(RoundPeriod period) {
		instance.period = period;
	}

	public static int getRound() {
		return instance.round;
	}

	public static RoundPeriod getRoundPeriod() {
		return instance.period;
	}

	public static void setPhase(String name) {
		instance.phase = name;
	}


	public static AxisAlignedBB getHub() {
		double[] defaults = {-10.0, 63.0, -10.0, 10.0, 63.0, 10.0};
		return getConfigZone("hub", defaults);
	}

	public static AxisAlignedBB getDoctorsRoom() {
		double[] defaults = {-60.0, 63.0, -10.0, -40.0, 63.0, 10.0};
		return getConfigZone("doctors", defaults);
	}

	public static AxisAlignedBB getMutantsRoom() {
		double[] defaults = {40.0, 63.0, -10.0, 60.0, 63.0, 10.0};
		return getConfigZone("mutants", defaults);
	}

	public static List<Vec3> getIndividualRooms() {
		double[] defaults = {};
		double[] coords = instance.config.get("places", "rooms", defaults).getDoubleList();
		ArrayList<Vec3> rooms = new ArrayList<Vec3>();

		for(int i=0 ; i < coords.length-2 ; i+=3) {
			rooms.add(new Vec3(coords[i], coords[i+1], coords[i+2]));
		}

		return rooms;
	}


	public static boolean devMode() {
		return instance.config.get(Configuration.CATEGORY_GENERAL, "dev", false).getBoolean(false);
	}


	private static AxisAlignedBB getConfigZone(String name, double[] defaults) {
		double[] zone = instance.config.get("places", name, defaults).getDoubleList();
		if(zone.length != 6)
			zone = defaults;
		return new AxisAlignedBB(zone[0], zone[1], zone[2], zone[3], zone[4], zone[5]);
	}
}
