package net.pikrass.sporzmc;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import net.pikrass.sporzmc.commands.CommandSporz;

import java.util.HashMap;
import java.util.Map;

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
		event.registerServerCommand(new CommandSporz());
		players = new HashMap<String, MCPlayer>();
	}



	private Configuration config;
	private Map<String, MCPlayer> players;

	public static Map<String, MCPlayer> getPlayers() {
		return instance.players;
	}

	public static boolean devMode() {
		return instance.config.get(Configuration.CATEGORY_GENERAL, "dev", false).getBoolean(false);
	}
}
