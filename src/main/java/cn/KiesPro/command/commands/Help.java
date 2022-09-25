package cn.KiesPro.command.commands;

import cn.KiesPro.Client;
import cn.KiesPro.command.Command;

public class Help extends Command {

    public Help() {
        super("Help", "?", " ", "help");
    }

    @Override
    public void onCommand(String[] args, String command) {
        Client.instance.sendMessage("§7§m----------------------------------------");
        Client.instance.sendMessage("§c§lKies §7- §f" + Client.instance.CLIENT_VERSION);
        Client.instance.sendMessage("§7§oDeveloped by KiesPro");
        Client.instance.sendMessage(" ");
        Client.instance.sendMessage("§c§lCommands:");
        Client.instance.sendMessage("§c.hide §7- Hides a module by name");
        //Client.instance.sendMessage("§c.vclip §7- Clips you down or up");
        Client.instance.sendMessage("§c.bind §7- Binds a module");
        //Client.instance.sendMessage("§c.rename §7- Changes the client name");
        //Client.instance.sendMessage("§c.name §7- Copies your Minecraft username to clipboard");
        Client.instance.sendMessage("§c.toggle §7- Toggles a module");
        //Client.instance.sendMessage("§c.hclip §7- Clips you horizontal");
        Client.instance.sendMessage("§7§m----------------------------------------");
    }
}
