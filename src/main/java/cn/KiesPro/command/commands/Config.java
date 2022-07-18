package cn.KiesPro.command.commands;

import cn.KiesPro.Client;
import cn.KiesPro.command.Command;

public class Config extends Command {
	
    public void execute(String[] args) {
        if (args.length != 1) {
            String type = args[0];
            String name = args[1];
            //idk error
            switch (type) {
            	case "save":
            		Client.instance.sendMessage("Successful save " + name);
                    try {
                        Client.instance.saveLoad.save(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public String getName() {
        return "config";
    }

    public String getSyntax() {
        return ".config <action> <filename>";
    }

    public String getDesc() {
        return "Loads and saves configs";
    }

    public String getAll() {
        return getSyntax() + " - " + getDesc();
    }
}