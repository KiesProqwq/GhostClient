package cn.KiesPro.module.player;

import java.lang.reflect.Field;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FastPlace extends Module {
	
	public static Field r = null;
	   
	public FastPlace() {
		super("FastPlace", "Make you put block quick", Category.PLAYER);
		Client.instance.settingsManager.rSetting(new Setting("Speed", this, 0, 0, 4, true));
		
		//Raven B Plus
	      try {
	          r = mc.getClass().getDeclaredField("field_71467_ac");
	       } catch (Exception var4) {
	          try {
	             r = mc.getClass().getDeclaredField("rightClickDelayTimer");
	          } catch (Exception var3) {
	          }
	       }

	       if (r != null) {
	          r.setAccessible(true);
	       }
	       
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent e) {
		if (Client.instance.destructed) {
			return;
		}
		
        int c = (int)Client.instance.settingsManager.getSettingByName(this,"Speed").getValDouble();
        
		if (e.phase == Phase.END) {
			try {
	               if (c == 0) {
	                  r.set(mc, 0);
	               } else {
	                  if (c == 4) {
	                     return;
	                  }

	                  int d = r.getInt(mc);
	                  if (d == 4) {
	                     r.set(mc, c);
	                  }
	               }
            } catch (Exception no) {
                this.toggle();
            }
	     }
	}
}
	


