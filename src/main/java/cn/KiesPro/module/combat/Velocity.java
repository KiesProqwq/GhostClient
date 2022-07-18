package cn.KiesPro.module.combat;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Velocity extends Module {

	public Velocity() {
		super("Velocity", "Anti Know Back", Category.COMBAT);
		Client.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90, 0, 100, true));
		Client.instance.settingsManager.rSetting(new Setting("Vertical", this, 100, 0, 100, true));
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e) {
		if (Client.instance.destructed) {
			return;
		}
		if (mc.thePlayer == null) {
			return;
		}
		float horizontal = (float) Client.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
		float vertical = (float) Client.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
		
		if (mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime && mc.thePlayer.maxHurtTime > 0) {
			mc.thePlayer.motionX *= (float) horizontal / 100;
			mc.thePlayer.motionY *= (float) vertical / 100;
			mc.thePlayer.motionZ *= (float) horizontal / 100;
		}
	}
}
