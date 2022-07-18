package cn.KiesPro.command.commands;

import cn.KiesPro.Client;
import cn.KiesPro.command.Command;

public class Help extends Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDesc() {
        return "Gives you the syntax of all commands and what they do.";
    }

    @Override
    public String getSyntax() {
        return ".help";
    }

    @Override
    public void execute(String[] args) {
        if(args.length != 1) {
            for(Command c : Client.instance.commandManager.getCommands()) {
                msg(c.getSyntax() + " §7- " + c.getDesc());
            }
        }

    }
}
