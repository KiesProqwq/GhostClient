package cn.KiesPro.module.blatant;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.ui.notifiction.Notification;
import cn.KiesPro.ui.notifiction.NotificationManager;
import cn.KiesPro.ui.notifiction.NotificationType;

public class BlatantMode extends Module {
	private boolean count = false;

	public BlatantMode() {
		super("BlatantMode", "如果你没有打开这个功能，你无法打开", Category.BLATANT);
	}
	
	@Override
	public void onEnable() {
//		if (count = false) {
//			Client.instance.sendMessage("请再次点击确认开启");
//			count = true;
//			Client.instance.moduleManager.getModule("BlatantMode").toggle();
//		}
//		Client.instance.blatant = true;
		NotificationManager.add(new Notification(NotificationType.ERROR, "T", "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT", 10000));
		NotificationManager.add(new Notification(NotificationType.SUCCESS, "Hello", "QwQ", 1000));
		NotificationManager.add(new Notification(NotificationType.INFO, "Hello", "QwQ", 2000));
		NotificationManager.add(new Notification(NotificationType.WARNING, "Hello", "QwQ", 4000));
		this.toggle();
	}
	
	@Override
	public void onDisable() {
		Client.instance.blatant = false;
	}
}
