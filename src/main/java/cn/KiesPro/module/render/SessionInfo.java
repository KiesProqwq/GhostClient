package cn.KiesPro.module.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventReceivePacket;
import cn.KiesPro.event.events.EventRender2D;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.handshake.client.C00Handshake;

public class SessionInfo extends Module {
	
	private int width = 170;
	private int height = 60;
	
    private int HM = 0;
    private int H  = 0; //小时
    private int M  = 0; //分钟
    private int S  = 0; //秒
    private long startTime;
	
	public SessionInfo() {
		super("SessionInfo", "none", Category.RENDER);
		registerSetting(new Setting("PosX", this, 500D, 0D, 1000D, false));
		registerSetting(new Setting("PosY", this, 500D, 0D, 1000D, false));
	}
	
	@EventTarget
	public void onRender(EventRender2D e) {
	    FontRenderer fr = mc.fontRendererObj;
	    
	    int x = (int) Client.instance.settingsManager.getSettingByName(this, "PosX").getValDouble();
	    int y = (int) Client.instance.settingsManager.getSettingByName(this, "PosY").getValDouble();
	    
	    String username = mc.getSession().getUsername();
	    
        Gui.drawRect(x, y, x + width, y + height, new Color(0, 0, 0, 140).getRGB());
        Gui.drawRect(x, y, x + width, (int)(y + 0.8), new Color(255, 255, 255, 255).getRGB());
        fr.drawString("Session Infomation", x + 5, y + 4, new Color(255, 255, 255, 255).getRGB());
        fr.drawString(username, x + 17, y + 15, new Color(255, 255, 255, 255).getRGB());
        //RenderUtil.drawImage(mc.getSession().getHead(), x + 4, PosY + 13, 10, 10);
        fr.drawString("Current Config: " + "Default" , x + 4, y + 25, new Color(255, 255, 255, 255).getRGB());
        
        GL11.glPushMatrix();
        //push 和 pop 控制了scale的范围
        GlStateManager.scale(2.25, 2.25, 2.25);
        fr.drawString(H + "h " + M + "m " + S + "s " ,(int) ((x + 5) / 2.25) ,(int)((y + 37) / 2.25) , new Color(255, 255, 255, 255).getRGB());
        GL11.glPopMatrix();
	}
	
	@EventTarget
	public void getJoinSeverTimer(EventReceivePacket e) {
		if (e.getPacket() instanceof C00Handshake) {
            startTime = System.currentTimeMillis();
            S = 0;
            M = 0;
            H = 0;
		}
		
        if (System.currentTimeMillis() - startTime > 1000) {
            S = (S + 1);
            startTime = System.currentTimeMillis();
        }
        if (S > 59) {
            S = 0;
            M = M + 1;
        }
        if (M > 59) {
            M = 0 ;
            H = H + 1;
        }
	}
}
