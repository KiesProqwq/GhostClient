package cn.KiesPro.command.commands;

import cn.KiesPro.Client;
import cn.KiesPro.command.Command;
import cn.KiesPro.module.Module;

public class Hide extends Command {

    public Hide() {
        super("Hide", "Hides a module by name", "hide <module name>", "h");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("clear")) {
                for (Module m : Client.instance.moduleManager.getModuleList())
                    m.setVisible(true);
                return;
            }
            for (Module m : Client.instance.moduleManager.getModuleList()) {
                if (m.getName().equalsIgnoreCase(args[0])) {
                	Client.instance.sendMessage((m.isVisible() ? "Hidden " : "Now showing ") + m.getName());
                    m.setVisible(!m.isVisible());
                }
            }
        }
    }
}
