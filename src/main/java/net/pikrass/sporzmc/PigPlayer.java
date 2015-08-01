package net.pikrass.sporzmc;

import net.pikrass.sporz.*;
import net.pikrass.sporz.events.*;
import net.pikrass.sporz.actions.*;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ChatComponentText;

public class PigPlayer extends MCPlayer {
	private EntityPig entity;

	public PigPlayer(String username, EntityPig pig) {
		super(username);
		this.entity = pig;
	}

	protected Entity getEntity() {
		return entity;
	}

	protected void sendMsg(String message) {
		message = "<"+getName()+"> " + message;

		ServerConfigurationManager manager =
			MinecraftServer.getServer().getConfigurationManager();
		manager.sendChatMsg(new ChatComponentText(message));
	}
}
