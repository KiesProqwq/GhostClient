package cn.KiesPro.command;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public abstract class Command {
	
    public abstract void execute(String[] args);
    
    public abstract String getName();
    public abstract String getSyntax();
    public abstract String getDesc();

    public static void msg(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§f[" + ChatFormatting.RED + "K§f" + "] " + msg));
    }
    
}
