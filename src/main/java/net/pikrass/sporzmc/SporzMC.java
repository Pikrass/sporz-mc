package net.pikrass.sporzmc;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import net.pikrass.sporzmc.commands.CommandSporz;

import net.pikrass.sporz.Game;
import net.pikrass.sporz.CustomRules;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@Mod(
	modid   = SporzMC.MODID,
	name    = SporzMC.MODNAME,
	version = SporzMC.MODVER,
	serverSideOnly = true,
	acceptableRemoteVersions = "*"
)

public class SporzMC
{
	public static final String MODID = "Sporz MC";
	public static final String MODNAME = "Sporz MC";
	public static final String MODVER = "0.0";

	@Instance(SporzMC.MODID)
	public static SporzMC instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		this.config = new Configuration(event.getSuggestedConfigurationFile());
		this.config.load();
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		command = new CommandSporz();
		players = new HashMap<String, MCPlayer>();
		rules = new CustomRules();
		event.registerServerCommand(command);
	}


	private CommandSporz command;

	public static CommandSporz getCommand() {
		return instance.command;
	}

	private Configuration config;
	private Game game;
	private Map<String, MCPlayer> players;
	private CustomRules rules;

	public static void startGame() {
		instance.game.start();
	}

	public static Game getGame() {
		return instance.game;
	}

	public static Game initGame() {
		if(instance.game != null)
			instance.game.end();
		instance.game = new Game();
		instance.game.setMaster(new MasterEventReceiver());
		return instance.game;
	}

	public static void endGame() {
		if(instance.game != null)
			instance.game.end();
		instance.game = null;
	}

	public static Map<String, MCPlayer> getPlayers() {
		return instance.players;
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
