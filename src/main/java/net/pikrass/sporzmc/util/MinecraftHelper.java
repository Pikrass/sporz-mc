package net.pikrass.sporzmc.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.command.ICommandSender;

public class MinecraftHelper
{
	public static IChatComponent red(String m) {
		return color(m, EnumChatFormatting.RED);
	}

	public static IChatComponent dgreen(String m) {
		return color(m, EnumChatFormatting.DARK_GREEN);
	}

	public static IChatComponent green(String m) {
		return color(m, EnumChatFormatting.GREEN);
	}

	public static IChatComponent blue(String m) {
		return color(m, EnumChatFormatting.BLUE);
	}

	public static IChatComponent gold(String m) {
		return color(m, EnumChatFormatting.GOLD);
	}

	private static IChatComponent color(String m, EnumChatFormatting color) {
		IChatComponent msg = new ChatComponentText(m);
		msg.getChatStyle().setColor(color);
		return msg;
	}

	public static void sendMsg(ICommandSender recv, String msg) {
		recv.addChatMessage(new ChatComponentText(msg));
	}

	public static void sendMsg(ICommandSender recv, IChatComponent msg) {
		recv.addChatMessage(msg);
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
