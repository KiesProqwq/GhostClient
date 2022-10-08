package cn.KiesPro.module;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventManager;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.ui.notifiction.Notification;
import cn.KiesPro.ui.notifiction.NotificationManager;
import cn.KiesPro.ui.notifiction.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Module {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	private String name, description;
	private int key;
	private Category category;
	private boolean toggled;
	private boolean visible = true;
	
	public Module(String name, String description, Category category) {
		super();
		this.name = name;
		this.description = description;
		this.key = 0;
		this.category = category;
		this.toggled = false;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
		if (Client.instance.configmanager != null) {
			Client.instance.configmanager.save();
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		
		if (this.toggled) {
			this.onEnable();
			NotificationManager.add(new Notification(NotificationType.SUCCESS, "Enable", getName() + " Enable.", 1000));
		} else {
			this.onDisable();
			NotificationManager.add(new Notification(NotificationType.ERROR, "Disable", getName() + " Disable.", 1000));
		}
		if (Client.instance.configmanager != null) {
			Client.instance.configmanager.save();
		}
	}
	
	public void toggle() {
		this.toggled = !this.toggled;

		if (this.toggled) {
			this.onEnable();
			NotificationManager.add(new Notification(NotificationType.SUCCESS, "Enable", getName() + " Enable.", 1000));
		} else {
			this.onDisable();
			NotificationManager.add(new Notification(NotificationType.ERROR, "Disable", getName() + " Disable.", 1000));
		}
		if (Client.instance.configmanager != null) {
			Client.instance.configmanager.save();
		}
	}
	
	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);
		EventManager.register(this);
	}
	
	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);
		EventManager.unregister(this);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
    public void registerSetting(Setting SETIN){
        Client.instance.settingsManager.rSetting(SETIN);
    }
    
	public void onUpdate() {}
	
	public void onTick() {}
	
    public void updateVals() {}
}
