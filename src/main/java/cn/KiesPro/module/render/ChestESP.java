package cn.KiesPro.module.render;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.RenderUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestESP extends Module {

	public ChestESP() {
		super("ChestESP", "Render chest", Category.RENDER);
		registerSetting(new Setting("Chest", this, true));
		registerSetting(new Setting("EnderChest", this, true));
	}
	
    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
    	//for遍历全部的Tile生物
    	  for (TileEntity e : mc.theWorld.loadedTileEntityList) {
    		  //这种if关系就好了
    		  if (Client.instance.settingsManager.getSettingByName(this, "Chest").isEnabled()) {
    			  if (e instanceof TileEntityChest) {
            		  RenderUtil.renderChest(e.getPos());
    			  }
    		  }
    		  
    		  if (Client.instance.settingsManager.getSettingByName(this, "EnderChest").isEnabled()) {
    			  if ((e instanceof TileEntityEnderChest)) {
            		  RenderUtil.renderEnderChest(e.getPos());
    			  }

    		  }
    	  }
    }
    
}
