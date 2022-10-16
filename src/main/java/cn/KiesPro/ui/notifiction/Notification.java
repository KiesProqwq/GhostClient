package cn.KiesPro.ui.notifiction;

import java.awt.Color;

import cn.KiesPro.ui.font.FontLoaders;
import cn.KiesPro.utils.AnimationUtil;
import cn.KiesPro.utils.RenderUtil;
import cn.KiesPro.utils.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class Notification {
	
    private NotificationType type;
    //文字
    private String title;
    private String messsage;
    
    private long start;
    public long end;
    
    private long fadedIn;
    private long fadeOut;
    double animationX;
    
    FontLoaders font;
    public TimerUtils timer = new TimerUtils();
    
    /*
     * Notification
     * 模式
     * 标题文字
     * 正文文字
     * 时间
     */
    public Notification(NotificationType type, final String title, final String messsage, final int time) {
        this.type = type;
        this.title = title;
        this.messsage = messsage;
        
        fadedIn = 200 * time;
        fadeOut = fadedIn + 500 * time;
        
        this.timer.reset();
        this.end = time;
    }
    
    public void show() {
        this.start = System.currentTimeMillis();
    }
    
    public boolean isShown() {
        return this.getTime() <= this.end;
    }
    
    long getTime() {
        return System.currentTimeMillis() - this.start;
    }
    
    public void render(final int count) {
        final Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution sr = new ScaledResolution(mc);
        
        Color color = new Color(0, 0, 0, 0);
        Color color2 = new Color(0, 140, 255);

        String icon = null;
        
        switch(this.type) {
			case INFO :
				color2 = new Color(255, 255, 255);
				icon = "KiesPro/notifictions/INFO.png";
				break;
			case WARNING :
				color2 = new Color(255, 255, 0);
				icon = "KiesPro/notifictions/WARNING.png";
				break;
			case ERROR :
	            color2 = new Color(204, 0, 18);
				icon = "KiesPro/notifictions/ERROR.png";
	            break;
			case SUCCESS :
	            color2 = new Color(46, 204, 113);
				icon = "KiesPro/notifictions/SUCCESS.png";
	            break;
        }
        
        final double x = sr.getScaledWidth() - 45 - font.regular18.getStringWidth(messsage) + animationX;
        final double y = sr.getScaledHeight() - 47 * count + 2;
        final double w = sr.getScaledWidth() - 5 + animationX;
        final double h = 30.0;
        
        this.animationX = AnimationUtil.getAnimationState(animationX, !isShown() ? x : 0.0D, Math.max(!isShown() ? 200D : 30, Math.abs(animationX - (!isShown() ? x : 0.0D)) * 1.0D) * 0.3D);
        
        final float health = (float)this.timer.getTime();
        double hpPercentage = health / this.end;
        hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0, 1.0);
        final double hpWidth = (45 + font.regular18.getStringWidth(messsage)) * hpPercentage;
        final double progress = AnimationUtil.INSTANCE.animate(hpWidth, 5.0, (double)(this.end / 1000L));

        RenderUtil.drawRect(x, y, w, y + h, color.getRGB()); //background
        RenderUtil.drawRect(x, y + 30.0, x + progress, y + 28.0, color2.getRGB()); //进度条
        
        font.regular18.drawStringWithShadow(title, (float)x + 28.0f, (float)y + 5.0f, new Color(255, 255 , 255, 255).getRGB());
        font.regular18.drawStringWithShadow(messsage, (float)x + 28.0f, (float)y + 15.0f, new Color(255, 255 , 255, 255).getRGB());
        RenderUtil.drawImage(new ResourceLocation(icon), x+5, y+5, 20, 20, color2.getRGB());
        //xy wh
        
//        if (this.type == NotificationType.INFO) {
//        	RenderUtil.drawBorderedRoundedRect((float)x + 4.0f, (float)y + 5.0f, (float)x + 22.0f, (float)y + 22.0f, 19.0f, 2.0f, -1, new Color(0, 0, 0, 0).getRGB());
//            mc.fontRendererObj.drawString("i", ((int)x + 12.5), (int)y + 9.5, new Color(0, 0, 0, 255).getRGB());
//        }
//        else if (this.type == NotificationType.WARNING) {
//            mc.fontRendererObj.drawString("\u26a0", x + 12.5, y + 9.5, new Color(255, 255, 0, 255).getRGB());
//        }
//        else if (this.type == NotificationType.ERROR) {
//            mc.fontRendererObj.drawString("\u26a0", x + 10.0, y + 9.5, new Color(204, 0, 18, 255).getRGB());
//        }
//        else if (this.type == NotificationType.SUCCESS) {
//            mc.fontRendererObj.drawString("\u2714", x + 9.5, y + 9.5, new Color(0, 255, 0, 255).getRGB());
//        }
    }
}