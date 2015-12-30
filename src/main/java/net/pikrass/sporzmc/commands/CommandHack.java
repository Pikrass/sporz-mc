package net.pikrass.sporzmc.commands;

import static net.pikrass.sporzmc.util.I18n.*;
import static net.pikrass.sporzmc.util.MinecraftHelper.*;

import net.pikrass.sporzmc.SporzMC;
import net.pikrass.sporzmc.handlers.CmdHackHandler;

import net.pikrass.sporz.actions.InvalidChoiceException;
import net.pikrass.sporz.Player;

import net.minecraft.command.ICommandSender;

import java.util.Set;
import java.util.Map;
import java.util.TreeMap;

public class CommandHack extends ActionCommand<CmdHackHandler> {
	@Override
	public String getName() {
		return _("hack");
	}

	@Override
	public String getCommandShortUsage(ICommandSender sender, CmdHackHandler handler) {
		return _("hack") + " " + getChoiceString(handler);
	}

	@Override
	public String getCommandUsage(ICommandSender sender, CmdHackHandler handler) {
		return _("hack") + " " + getChoiceString(handler);
	}

	@Override
	protected void execute(ICommandSender sender, CmdHackHandler handler, String[] params) {
		if(params.length != 1) {
			printShortUsage(sender);
			return;
		}

		String role;
		if(params[0].equals(_("none"))) {
			role = null;
		} else {
			Map<String, String> choices = makeChoices(handler);
			if(!choices.containsKey(params[0])) {
				printShortUsage(sender);
				return;
			}
			role = choices.get(params[0]);
		}

		sendMsg(sender, green(_("Your choice has been saved")));
		try {
			handler.hack(role);
		} catch(InvalidChoiceException e) {
		}
	}


	private Map<String, String> makeChoices(CmdHackHandler handler) {
		Set<String> codes = handler.getHackableRoles();
		TreeMap<String, String> choices = new TreeMap<String, String>();

		int numberPsy = 0, numberGenet = 0, numberEng = 0;
		for(String code : codes) {
			switch(code.charAt(0)) {
				case 'p': numberPsy++; break;
				case 'g': numberGenet++; break;
				case 'c': numberEng++; break;
			}
		}

		for(String code : codes) {
			String uiName = "ERROR";
			int num = 1;

			switch(code.charAt(0)) {
				case 'p': uiName = _("hack choice", "psy");   num = numberPsy;   break;
				case 'g': uiName = _("hack choice", "genet"); num = numberGenet; break;
				case 'c': uiName = _("hack choice", "eng");   num = numberEng;   break;
			}

			if(num > 1)
				uiName += code.substring(1);

			choices.put(uiName, code);
		}

		return choices;
	}

	private String getChoiceString(CmdHackHandler handler) {
		StringBuffer str = new StringBuffer();
		for(String choice : makeChoices(handler).keySet()) {
			str.append(choice);
			str.append("|");
		}
		str.append(_("none"));

		return str.toString();
	}
}
