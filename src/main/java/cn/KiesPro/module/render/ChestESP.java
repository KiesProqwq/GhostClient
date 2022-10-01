package cn.KiesPro.module.render;

import java.awt.Color;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventRender3D;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.RenderUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

public class ChestESP extends Module {

	public ChestESP() {
		super("ChestESP", "Render chest", Category.RENDER);
		registerSetting(new Setting("Chest", this, true));
		registerSetting(new Setting("EnderChest", this, true));
	}
	
    @EventTarget
    public void onRenderWorld(EventRender3D event) {
    	//for遍历全部的Tile生物
    	  for (TileEntity e : mc.theWorld.loadedTileEntityList) {
    		  //这种if关系就好了
    		  if (Client.instance.settingsManager.getSettingByName(this, "Chest").isEnabled()) {
    			  if (e instanceof TileEntityChest) {
    				  //yellow
            		  RenderUtil.chestESPBox((TileEntity)e, 0, new Color(255, 170, 0, 1));
    			  }
    		  }
    		  
    		  if (Client.instance.settingsManager.getSettingByName(this, "EnderChest").isEnabled()) {
    			  if ((e instanceof TileEntityEnderChest)) {
    				  //purple
            		  RenderUtil.chestESPBox((TileEntity)e, 0, new Color(170, 0, 170, 1));
    			  }
    		  }
    	  }
    }
}
