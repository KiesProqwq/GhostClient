package cn.KiesPro.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.KiesPro.Client;
import cn.KiesPro.command.commands.*;
import cn.KiesPro.event.eventapi.EventManager;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventChat;

public class CommandManager {

	public List<Command> commands = new ArrayList<Command>();
	public String prefix = ".";

	public CommandManager() {
		setup();
		EventManager.register(this);
	}

	public void setup() {
		commands.add(new Help());
		commands.add(new Bind());
		commands.add(new Hide());
		commands.add(new Toggle());
		//commands.add(new Config());
	}
	
	@EventTarget
	public void handleChat(EventChat event) {
		String message = event.getMessage();
		//SelfDestruct
		//if (Client.instance.destructed)
		//	return;
		
		//NoCommand开启就返回
		if (Client.instance.moduleManager.getModule("NoCommand").isToggled())
			return;
		//开头不带.就返回
		if(!message.startsWith(prefix))
			return;

		event.setCancelled(true);

		message = message.substring(prefix.length());

		boolean foundCommand = false;

		if(message.split(" ").length > 0) {
			String commandName = message.split(" ")[0];

			for(Command c : commands) {
				if(c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
					c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
					foundCommand = true;
					break;
				}
			}
		}

		if(!foundCommand) {
			Client.instance.sendMessage("Could find the command.");
		}
	}
}

//Command.msg("The command \"§9" + ranCmd + "§7\" has not been found!");
