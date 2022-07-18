package cn.KiesPro;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import cn.KiesPro.clickgui.old.ClickGui;
import cn.KiesPro.command.CommandManager;
import cn.KiesPro.file.SaveLoad;
import cn.KiesPro.module.Module;
import cn.KiesPro.module.ModuleManager;
import cn.KiesPro.settings.SettingsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Client
{
	public String CLIENT_NAME = "Kies";
	public String CLIENT_VERSION = "1.5";
	
	public String prefix = "§f[" + ChatFormatting.RED + "K§f" + "]";
	
	private final static Minecraft mc = Minecraft.getMinecraft();
	
    public static Client instance;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public CommandManager commandManager;
    
    public ClickGui oldClickGui;
    
    public SaveLoad saveLoad;
    
    public boolean destructed = false;
    
    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        
    	settingsManager = new SettingsManager();
    	moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        
        oldClickGui = new ClickGui();
        
    	saveLoad = new SaveLoad();
    	
    	System.out.println("KiesPro is Load");
    }
    
    public void sendMessage(String message) {
        message = "§f[" + ChatFormatting.RED + "K§f" + "] " + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    
    
    @SubscribeEvent
    public void key(KeyInputEvent e) {
    	if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null)
    		return; 
    	try {
             if (Keyboard.isCreated()) {
                 if (Keyboard.getEventKeyState()) {
                     int keyCode = Keyboard.getEventKey();
                     if (keyCode <= 0)
                    	 return;
                     for (Module m : moduleManager.modules) {
                    	 if (m.getKey() == keyCode && keyCode > 0) {
                    		 m.toggle();
                    	 }
                     }
                 }
             }
         } catch (Exception q) { q.printStackTrace(); }
    }
    
    public void onDestruct() {
    	if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().thePlayer != null) {
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    	}
    	destructed = true;
    	MinecraftForge.EVENT_BUS.unregister(this);
    	for (int k = 0; k < this.moduleManager.modules.size(); k++) {
    		Module m = this.moduleManager.modules.get(k);
    		MinecraftForge.EVENT_BUS.unregister(m);
    		this.moduleManager.getModuleList().remove(m);
    	}
    	this.moduleManager = null;
    	this.oldClickGui = null;
    }
    
	public static boolean check() {
		return mc.thePlayer != null && mc.theWorld != null;
	}
}
