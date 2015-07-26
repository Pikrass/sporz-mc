package net.pikrass.sporzmc.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.command.ICommandSender;

public class MinecraftHelper
{
	private static final String
		BLACK      = "§0",
		DBLUE      = "§1",
		DGREEN     = "§2",
		DAQUA      = "§3",
		DREQ       = "§4",
		DPURPLE    = "§5",
		GOLD       = "§6",
		GRAY       = "§7",
		DGRAY      = "§8",
		BLUE       = "§9",
		GREEN      = "§a",
		AQUA       = "§b",
		RED        = "§c",
		PURPLE     = "§d",
		YELLOW     = "§e",
		WHITE      = "§f",
		OBFUSCATED = "§k",
		BOLD       = "§l",
		STRIKE     = "§m",
		UNDERLINE  = "§n",
		ITALIC     = "§o",
		RESET      = "§r";

	public static String red(String m) {
		return RED+m+RESET;
	}

	public static String dgreen(String m) {
		return DGREEN+m+RESET;
	}

	public static String green(String m) {
		return GREEN+m+RESET;
	}

	public static void sendMsg(ICommandSender recv, String msg) {
		recv.addChatMessage(new ChatComponentText(msg));
	}

	public static boolean isOp(String username) {
		username = username.toLowerCase().trim();
		String[] ops = MinecraftServer.getServer().getConfigurationManager()
			.getOppedPlayers().getKeys();

		for(String name : ops) {
			if(name.toLowerCase().equals(username))
				return true;
		}

		return false;
	}

	private MinecraftHelper() {
	}
}
