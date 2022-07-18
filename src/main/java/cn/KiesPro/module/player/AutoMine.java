package cn.KiesPro.module.player;

import org.lwjgl.input.Mouse;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.utils.TimerUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class AutoMine extends Module {
	
	TimerUtils timer = new TimerUtils();
    int i;
    
	public AutoMine() {
		super("AutoMine", "×Ô¶¯ÍÚ¾ò", Category.PLAYER);
	}
	
	@Override
    public void onEnable() {
        super.onEnable();
        this.timer.reset();
    }
    
	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (Client.instance.destructed) {
			return;
		}

        if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null && !mc.theWorld.isAirBlock(mc.objectMouseOver.getBlockPos()) && !Mouse.isButtonDown(0)) {
            i = mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(i, true);
            KeyBinding.onTick(i);
            this.timer.reset();
        } else if (this.timer.hasReached(30.0D)) {
            i = mc.gameSettings.keyBindAttack.getKeyCode();
            KeyBinding.setKeyBindState(i, false);
        }
	}
}
