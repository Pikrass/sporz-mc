package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import net.pikrass.sporzmc.*;
import net.pikrass.sporz.CustomRules;
import net.pikrass.sporz.Role;

import java.util.List;
import java.util.LinkedList;

public class CommandRules extends SporzSubcommand
{
	@Override
	public String getName() {
		return _("rules");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender) {
		return _("rules (init|set <var> <num>)");
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return
			_("rules")+"\n"+
			_("rules init")+"\n"+
			_("rules set <var> <num>");
	}

	@Override
	public void execute(ICommandSender sender, String[] params) {
		if(params.length == 0) {
			showRules(sender);
		} else if(params[0].equals(_("init"))) {
			initRules(sender);
		} else if(params.length == 3 && params[0].equals(_("set"))) {
			try {
				setRule(sender, params[1], Integer.parseInt(params[2]));
			} catch(NumberFormatException e) {
				printShortUsage(sender);
			}
		} else {
			printShortUsage(sender);
		}
	}

	private void showRules(ICommandSender sender) {
		CustomRules rules = SporzMC.getRules();

		String res =
			String.format(_("HOSTS = %d"), rules.getHosts())+"\n"+
			String.format(_("RESISTANTS = %d"), rules.getResistants())+"\n"+
			String.format(_("MUTANTS = %d"), rules.getHosts())+"\n"+
			String.format(_("DOCTORS = %d"), rules.getRole(Role.DOCTOR))+"\n"+
			String.format(_("PSYCHOLOGISTS = %d"), rules.getRole(Role.PSYCHOLOGIST))+"\n"+
			String.format(_("GENETICISTS = %d"), rules.getRole(Role.GENETICIST))+"\n"+
			String.format(_("COMPUTER_ENGINEERS = %d"), rules.getRole(Role.COMPUTER_ENGINEER))+"\n"+
			String.format(_("HACKERS = %d"), rules.getRole(Role.HACKER))+"\n"+
			String.format(_("SPIES = %d"), rules.getRole(Role.SPY))+"\n"+
			String.format(_("TRAITORS = %d"), rules.getRole(Role.TRAITOR));

		sendMsg(sender, res);
	}

	private void initRules(ICommandSender sender) {
		CustomRules rules = SporzMC.getRules();
		int nbPlayers = SporzMC.getPlayers().size();

		try {
			rules.setNbPlayers(nbPlayers);
			sendMsg(sender, green(_("Rules set")));
		} catch(IllegalArgumentException e) {
			sendMsg(sender, red(_("Not enough players")));
		}
	}

	private void setRule(ICommandSender sender, String rule, int num) {
		CustomRules rules = SporzMC.getRules();

		if(rule.equals(_("HOSTS"))) {
			rules.setHosts(num);
		} else if(rule.equals(_("RESISTANTS"))) {
			rules.setResistants(num);
		} else if(rule.equals(_("MUTANTS"))) {
			rules.setMutants(num);
		} else if(rule.equals(_("DOCTORS"))) {
			rules.setRole(Role.DOCTOR, num);
		} else if(rule.equals(_("PSYCHOLOGISTS"))) {
			rules.setRole(Role.PSYCHOLOGIST, num);
		} else if(rule.equals(_("GENETICISTS"))) {
			rules.setRole(Role.GENETICIST, num);
		} else if(rule.equals(_("COMPUTER_ENGINEERS"))) {
			rules.setRole(Role.COMPUTER_ENGINEER, num);
		} else if(rule.equals(_("HACKERS"))) {
			rules.setRole(Role.HACKER, num);
		} else if(rule.equals(_("SPIES"))) {
			rules.setRole(Role.SPY, num);
		} else if(rule.equals(_("TRAITORS"))) {
			rules.setRole(Role.TRAITOR, num);
		} else {
			sendMsg(sender, red(_("Unknown rule")));
			return;
		}

		sendMsg(sender, green(_("Rule set")));
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> list = new LinkedList<String>();
		if(args.length == 1) {
			if(_("Rules").startsWith(args[0]))   list.add(_("init"));
			if(_("set").startsWith(args[0]))    list.add(_("set"));
		} else if(args.length == 2 && args[0].equals(_("set"))) {
			if(_("HOSTS").startsWith(args[1]))              list.add(_("HOSTS"));
			if(_("RESISTANTS").startsWith(args[1]))         list.add(_("RESISTANTS"));
			if(_("MUTANTS").startsWith(args[1]))            list.add(_("MUTANTS"));
			if(_("DOCTORS").startsWith(args[1]))            list.add(_("DOCTORS"));
			if(_("PSYCHOLOGISTS").startsWith(args[1]))      list.add(_("PSYCHOLOGISTS"));
			if(_("GENETICISTS").startsWith(args[1]))        list.add(_("GENETICISTS"));
			if(_("COMPUTER_ENGINEERS").startsWith(args[1])) list.add(_("COMPUTER_ENGINEERS"));
			if(_("HACKERS").startsWith(args[1]))            list.add(_("HACKERS"));
			if(_("SPIES").startsWith(args[1]))              list.add(_("SPIES"));
			if(_("TRAITORS").startsWith(args[1]))           list.add(_("TRAITORS"));
		}
		return list;
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return isOp(sender.getName()) && SporzMC.getGame() == null;
	}
}
