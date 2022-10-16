package cn.KiesPro;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import cn.KiesPro.command.CommandManager;
import cn.KiesPro.file.ClickGuiFile;
import cn.KiesPro.file.ConfigManager;
import cn.KiesPro.module.Module;
import cn.KiesPro.module.ModuleManager;
import cn.KiesPro.settings.SettingsManager;
import cn.KiesPro.ui.clickgui.old.ClickGui;
import cn.KiesPro.ui.font.FontLoaders;
import cn.KiesPro.utils.ratsiel.auth.model.mojang.MinecraftAuthenticator;
import cn.KiesPro.utils.ratsiel.auth.model.mojang.MinecraftToken;
import cn.KiesPro.utils.ratsiel.auth.model.mojang.profile.MinecraftProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Session;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Client
{
	public String CLIENT_NAME = "Kies";
	public String CLIENT_VERSION = "1.7.2";
	
	public String prefix = "§f[" + ChatFormatting.RED + "K§f" + "]";
	//Minecraft Instance
	private final static Minecraft mc = Minecraft.getMinecraft();
	//instance
    public static Client instance;
    //Manager
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public CommandManager commandManager;
    public FontLoaders fontManager;
    //ClickGUI
    public ClickGui oldClickGui;
    public cn.KiesPro.ui.clickgui.hero.ClickGUI heroGui;
    //File
    public ClickGuiFile saveclickgui;
    public ConfigManager configmanager;
    //BlatantMode
    public boolean blatant = false;
    
    String account = "1511936608@qq.com";
    String password = "gxy070108";
    
    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        
    	settingsManager = new SettingsManager();
    	moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        
        oldClickGui = new ClickGui();
        
        saveclickgui =  new ClickGuiFile();
        configmanager = new ConfigManager();
    	
    	System.out.println("KiesPro is Load");
    	
    }
    
    /*
     * @username account
     * useage: mc.session = getSession(xxx, xxx);
     */
    public Session getSession(String username, String password) {
    	final MinecraftAuthenticator minecraftAuthenticator = new MinecraftAuthenticator();
        final MinecraftToken minecraftToken = minecraftAuthenticator.loginWithXbox(username, password);
        final MinecraftProfile minecraftProfile = minecraftAuthenticator.checkOwnership(minecraftToken);
    	return new Session(minecraftProfile.getUsername(), minecraftProfile.getUuid().toString(), minecraftToken.getAccessToken(), "mojang"); 
    }
    
    public void sendMessage(String message) {
        message = "§f[" + ChatFormatting.RED + "K§f" + "] " + message;
        mc.thePlayer.addChatMessage(new ChatComponentText(message));
    }
    
    
    @SubscribeEvent
    public void key(KeyInputEvent e) {
    	if (mc.theWorld == null || Minecraft.getMinecraft().thePlayer == null)
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
    
    
}
