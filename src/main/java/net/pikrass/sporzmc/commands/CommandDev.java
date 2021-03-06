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

public class CommandDev extends SporzSubcommand
{
	@Override
	public String getName() {
		return _("dev");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("dev pigs reset OR pigs <num> OR seed <num>");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return
			_("dev pigs reset")+"\n"+
			_("dev pigs <num>")+"\n"+
			_("dev seed <num>");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) {
		if(params.length == 2 && params[0].equals(_("pigs"))) {
			if(params[1].equals(_("reset"))) {
				resetPigs();
			} else {
				try {
					spawnPigs(Integer.parseInt(params[1]), sender.getPosition());
				} catch(NumberFormatException e) {
					printShortUsage(sender);
				}
			}
		} else if(params.length == 2 && params[0].equals(_("seed"))) {
			seedRNG(Long.parseLong(params[1]));
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

	private void seedRNG(long seed) {
		SporzMC.getRules().seedRNG(seed);
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> list = new LinkedList<String>();
		if(args.length == 1) {
			if(_("pigs").startsWith(args[0]))
				list.add(_("pigs"));
			if(_("seed").startsWith(args[0]))
				list.add(_("seed"));
		} else if(args.length == 2 && args[0].equals(_("pigs"))) {
			if(_("reset").startsWith(args[1]))
				list.add(_("reset"));
		}
		return list;
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		// Allow ops and console
		return isOp(sender.getName()) || sender.canUseCommand(100, "sporz dev");
	}
}
