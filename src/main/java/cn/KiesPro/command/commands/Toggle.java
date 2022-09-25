package cn.KiesPro.command.commands;

import cn.KiesPro.Client;
import cn.KiesPro.command.Command;
import cn.KiesPro.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Toggle extends Command {

	public Toggle() {
		super("toggle", "Toggles a module by name.", "t <module name>", "t");
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length > 0) {
			String moduleName = args[0];
			
			boolean foundModule = false;
			
			for(Module module : Client.instance.moduleManager.getModuleList()) {
				if(module.getName().equalsIgnoreCase(moduleName)) {
					module.toggle();

					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(module.isToggled() ? module.getName() + " " + "was enabled" : module.getName() + " " + "was disabled"));
					
					foundModule = true;
					break;
				}
			}
			
			if(!foundModule) {
				Client.instance.sendMessage("§8> §aCould not find the module.");
			}
		}
	}
}
