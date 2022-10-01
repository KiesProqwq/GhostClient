package cn.KiesPro.module.render;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.MathUtils;
import cn.KiesPro.utils.raven.RavenUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class FPSSpoof extends Module {
	
	public FPSSpoof() {
		super("FPSSpoof", "FPSSpoof", Category.RENDER);        
		Client.instance.settingsManager.rSetting(new Setting("MaxFPS", this, 150.0, 0.0, 10000, false));
		Client.instance.settingsManager.rSetting(new Setting("MinFPS", this, 150.0, 0.0, 10000, false));
	}
	
	@SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {		
        if (!RavenUtils.isPlayerInGame() || mc.currentScreen != null)
            return;
        
    	double max = Client.instance.settingsManager.getSettingByName(this, "MaxFPS").getValDouble();
    	double min = Client.instance.settingsManager.getSettingByName(this, "MinFPS").getValDouble();
    	
		setFPS((int)MathUtils.randomNumber(max, min));
    }
	
	private void setFPS(int fpsCounter) {
	        try {
	            ReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getMinecraft(), fpsCounter, "fpsCounter", "field_71420_M");
	        } catch (Throwable e) {
	            e.printStackTrace();
	        }
	    }
	
}
