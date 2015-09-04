package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.entity.passive.EntityPig;
import com.google.common.base.Predicate;

import net.pikrass.sporzmc.*;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

import java.lang.reflect.*;
public class CommandDev extends SporzSubcommand
{
	@Override
	public String getName() {
		return _("dev");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("dev [ pigs reset|<num> ] | [ <player> <cmd> ]");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return
			_("dev pigs reset")+"\n"+
			_("dev pigs <num>")+"\n"+
			_("dev <player> <cmd> <args>...");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) {
		if(params.length == 2 && params[0].equals("pigs")) {
			if(params[1].equals("reset")) {
				resetPigs();
			} else {
				try {
					spawnPigs(Integer.parseInt(params[1]), sender.getPosition());
				} catch(NumberFormatException e) {
					printShortUsage(sender);
				}
			}
		} else if(params.length >= 2) {
			MCPlayer player = SporzMC.getPlayers().get(params[0]);
			if(player == null)
				printShortUsage(sender);
			else
				sendCommandAs(player, Arrays.copyOfRange(params, 1, params.length));
		} else {
			printShortUsage(sender);
		}
	}

	private void resetPigs() {
		World world = MinecraftServer.getServer().worldServerForDimension(0);
		Map<String, MCPlayer> players = SporzMC.getPlayers();

		Predicate filter = new Predicate() {
			public boolean apply(Object input) {
				EntityPig pig = (EntityPig)input;
				return pig.getCustomNameTag() != null;
			}
			public boolean equals(Object object) { return false; }
		};

		for(Object o : world.getEntities(EntityPig.class, filter)) {
			EntityPig pig = (EntityPig)o;
			world.removeEntity(pig);
			players.remove(pig.getCustomNameTag());
		}
	}

	private void spawnPigs(int num, BlockPos pos) {
		World world = MinecraftServer.getServer().worldServerForDimension(0);
		Map<String, MCPlayer> players = SporzMC.getPlayers();

		for(int i=1 ; i <= num ; ++i) {
			EntityPig pig = new EntityPig(world);
			pig.setPosition(pos.getX(), pos.getY(), pos.getZ());
			pig.setCustomNameTag("Player"+i);
			world.spawnEntityInWorld(pig);

			PigPlayer player = new PigPlayer("Player"+i, pig);
			players.put("Player"+i, player);
		}
	}

	private void sendCommandAs(MCPlayer player, String[] args) {
		try {
			SporzMC.getCommand().execute(player.getCommandSender(), args);
		} catch(CommandException e) {
		}
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> list = new LinkedList<String>();
		if(args.length == 1) {
			if(_("pigs").startsWith(args[0]))
				list.add(_("pigs"));
			for(String name : SporzMC.getPlayers().keySet()) {
				if(name.startsWith(args[0]))
					list.add(name);
			}
		} else if(args.length == 2 && args[0].equals(_("pigs"))) {
			if(_("reset").startsWith(args[1]))
				list.add(_("reset"));
		} else if(args.length >= 2) {
			MCPlayer player = SporzMC.getPlayers().get(args[0]);
			if(player != null)
				return SporzMC.getCommand().addTabCompletionOptions(
						player.getCommandSender(),
						Arrays.copyOfRange(args, 1, args.length), pos);
		}
		return list;
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		// Allow ops and console
		return isOp(sender.getName()) || sender.canUseCommand(100, "sporz dev");
	}
}
