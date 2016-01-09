package net.pikrass.sporzmc;

import net.pikrass.sporz.*;
import net.pikrass.sporz.events.*;
import net.pikrass.sporz.actions.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandResultStats;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PigPlayer extends MCPlayer implements ICommandSender {
	private EntityPig entity;

	public PigPlayer(String username, EntityPig pig) {
		super(username);
		this.entity = pig;
	}

	protected Entity getEntity() {
		return entity;
	}

	@Override
	protected void sendMsg(String message) {
		net.pikrass.sporzmc.util.MinecraftHelper.sendMsg(this, message);
	}
	@Override
	protected void sendMsg(IChatComponent message) {
		net.pikrass.sporzmc.util.MinecraftHelper.sendMsg(this, message);
	}

	public ICommandSender getCommandSender() {
		return this;
	}

	public String func_70005_c_() {
		return super.getName();
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName());
	}

	@Override
	public void addChatMessage(IChatComponent msg) {
		ChatComponentText text = new ChatComponentText("<"+getName()+"> ");
		text.appendSibling(msg);

		ServerConfigurationManager manager =
			MinecraftServer.getServer().getConfigurationManager();
		manager.sendChatMsg(text);
	}

	@Override
	public boolean canUseCommand(int permLevel, String commandName) {
		return false;
	}

	@Override
	public BlockPos getPosition() {
		return entity.getPosition();
	}

	@Override
	public Entity getCommandSenderEntity() {
		return entity;
	}

	@Override
	public boolean sendCommandFeedback() {
		return true;
	}

	@Override
	public void setCommandStat(CommandResultStats.Type type, int amount) {
	}

	@Override
	public Vec3 getPositionVector() {
		return entity.getPositionVector();
	}

	@Override
	public World getEntityWorld() {
		return entity.getEntityWorld();
	}
}
