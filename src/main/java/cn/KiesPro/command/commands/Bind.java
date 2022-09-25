package cn.KiesPro.command.commands;

import org.lwjgl.input.Keyboard;

import cn.KiesPro.Client;
import cn.KiesPro.command.Command;
import cn.KiesPro.module.Module;

public class Bind extends Command {

	public Bind() {
		super("Bind", "Binds a module", "bind <name> <key> |q clear","b");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if (args.length == 2) {
			String moduleName = args[0];
			String keyName = args[1];
			boolean found = false;
			
			for (Module module : Client.instance.moduleManager.getModuleList()) {
				if (module.getName().equalsIgnoreCase(moduleName)) {
					module.setKey(Keyboard.getKeyIndex(keyName.toUpperCase()));
					Client.instance.sendMessage(String.format("Bound %s to %s", new Object[] { module.getName(), Keyboard.getKeyName(module.getKey()) }));
					found = true;
					break;
				}
			}
			if (!found)
				Client.instance.sendMessage("Could not find the module.");
		}
		
		if (args.length == 1) {
			boolean found = false;
			if (args[0].equalsIgnoreCase("clear")) {
				for (Module module : Client.instance.moduleManager.getModuleList()) {
					if (!module.getName().equalsIgnoreCase("clickgui"))
						module.setKey(0);
				}
				Client.instance.sendMessage("Cleared all your keybinds.");
				found = true;
			}
			if (!found)
				Client.instance.sendMessage("Could not find the module.");
		}
	}
}
