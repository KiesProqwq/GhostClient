package cn.KiesPro.module.player;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventUpdate;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.MathUtils;
import cn.KiesPro.utils.TimerUtils;
import cn.KiesPro.utils.raven.RavenUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;

public class ChestSteal extends Module {

	private TimerUtils time = new TimerUtils();
	
	public ChestSteal() {
		super("ChestSteal", "Steal things", Category.PLAYER);
		registerSetting(new Setting("Max Delay", this, 50.0, 0.0, 250.0, true));
		registerSetting(new Setting("Min Delay", this, 50.0, 0.0, 250.0, true));

		registerSetting(new Setting("Auto Close", this, true));
	}

	/*
	 * Tranlate note
	 * container 容器
	 * 
	 * 设计Max和Min
	 * 
	 * bug:如同eagle开启后永久开启了 所以也嵌套一层关系
	 */
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (Client.instance.moduleManager.getModule("ChestSteal").isToggled()) {

			if (!RavenUtils.isPlayerInGame())
				return;

			double max = Client.instance.settingsManager.getSettingByName(this, "Max Delay").getValDouble();
			double min = Client.instance.settingsManager.getSettingByName(this, "Min Delay").getValDouble();
			
			
			if (min >= max) {
				Client.instance.settingsManager.getSettingByName(this, "Max Delay").setValDouble(min + 1);
				min = Client.instance.settingsManager.getSettingByName(this, "Min Delay").getValDouble();
			}
			
			Container chest = mc.thePlayer.openContainer;
			if (chest != null && chest instanceof ContainerChest) {
				ContainerChest container = (ContainerChest) chest;
				for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
					if (container.getLowerChestInventory().getStackInSlot(i) != null) {
						if (time.hasReached(MathUtils.randomNumber(max, min))) {
							mc.playerController.windowClick(container.windowId, i, 0, 1, (EntityPlayer) mc.thePlayer);
							time.reset();
						}
					}
				}
				if (this.isContainerEmpty(chest) && Client.instance.settingsManager.getSettingByName(this, "Auto Close").getValBoolean()) {
					mc.thePlayer.closeScreen();
				}
			}
		}
	}

	private boolean isContainerEmpty(Container container) {
		int slotAmount;
		boolean temp = true;

		slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;

		for (int i = 0; i < slotAmount; ++i) {
			if (!container.getSlot(i).getHasStack())
				continue;
			temp = false;
		}
		return temp;
	}
	
}
