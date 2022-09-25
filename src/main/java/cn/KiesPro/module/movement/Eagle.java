package cn.KiesPro.module.movement;

import org.lwjgl.input.Keyboard;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.MathUtils;
import cn.KiesPro.utils.TimerUtils;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Eagle extends Module {
	
	private TimerUtils sneakTimer = new TimerUtils();
	
	public Eagle() {
		super("Eagle", "自动蹲搭", Category.MOVEMENT);
		
		registerSetting(new Setting("OnSneakKey", this, true));
		registerSetting(new Setting("Min Delay", this, 500D, 0D, 1500D, true));
		registerSetting(new Setting("Max Delay", this, 500D, 0D, 1500D, true));
		//registerSetting(new Setting("Debug", this, true));
	}
	
	@SubscribeEvent
	public void onTick(TickEvent e) {
		
		double max = Client.instance.settingsManager.getSettingByName(this, "Max Delay").getValDouble();
		double min = Client.instance.settingsManager.getSettingByName(this, "Min Delay").getValDouble();
		
		//计算Min是否大于Max 但由于写在了onTick下所以只有开启后有效果 但不会有什么影响有1点的差距
		if (min >= max) {
			Client.instance.settingsManager.getSettingByName(this, "Max Delay").setValDouble(min + 1);
			min = Client.instance.settingsManager.getSettingByName(this, "Max Delay").getValDouble();
		}
		
		//@SubscribeEvent 添加完后在关闭Module也会有效果所以写了这个判断
		if (Client.instance.moduleManager.getModule("Eagle").isToggled()) {
			
		double delay = MathUtils.randomNumber(max, min);
		int shiftkey = mc.gameSettings.keyBindSneak.getKeyCode();
		
		if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) return;
		
		if (mc.thePlayer != null && mc.theWorld != null) {
			if (Client.instance.settingsManager.getSettingByName(this, "OnSneakKey").getValBoolean() && !Keyboard.isKeyDown(shiftkey)) {
                KeyBinding.setKeyBindState(shiftkey, false);
			} else {
				if (mc.theWorld.getBlockState((new BlockPos(mc.thePlayer)).add(0, -1, 0)).getBlock() == Blocks.air && mc.thePlayer.onGround) {
                    KeyBinding.setKeyBindState(shiftkey, true);
                    
                    //if (Client.instance.settingsManager.getSettingByName(this, "Debug").getValBoolean()) {Client.instance.sendMessage(delay); }
                    	
                    this.sneakTimer.reset();
                } else if (this.sneakTimer.hasReached(delay)) {
                    KeyBinding.setKeyBindState(shiftkey, Keyboard.isKeyDown(shiftkey));
                } else {
                    KeyBinding.setKeyBindState(shiftkey, false);
                }
			}
		} else {
			KeyBinding.setKeyBindState(shiftkey, false);
			}
		}
	}
	
	public void onDisable() {
		super.onDisable();
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
	}
	public void onEnable() {
		super.onEnable();
	}
}
