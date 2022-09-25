package cn.KiesPro.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.FairySoulUtil;
import cn.KiesPro.utils.RenderUtil;
import cn.KiesPro.utils.raven.Utils;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FairySoulESP extends Module {
	
    private static List<String> loopList;
    private static List<String> loopListfound;
    
	private Setting map;
    ArrayList<String> sites = new ArrayList<String>();
    
	public FairySoulESP() {
		super("FairySoulESP", "Render FairySoul(仙女灵魂)", Category.RENDER);
		sites.add("hub");
		sites.add("islands");
		sites.add("den");
		sites.add("fortress");
		sites.add("mine");
		sites.add("caverns");
		sites.add("barn");
		sites.add("desert");
		sites.add("end");
		sites.add("darwen");
		sites.add("dungeons");
		
        this.registerSetting(map = new Setting("Map", this,"hub",sites));
	}
	
    @SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
        if (Client.instance.moduleManager.getModule("FairySoulESP").isToggled()) {
            fixLists();
        }
	}
	
    @SubscribeEvent
	public void onRender3D(RenderWorldLastEvent e) {
        if(!Utils.isPlayerInGame() && Client.instance.moduleManager.getModule("FairySoulESP").isToggled()) {
            for (int i = 0; i < loopList.size(); ++i) {
                double z;
                double y;
                String loc = loopList.get(i);
                String[] args = loc.split(",");
                double x = Integer.parseInt(args[0]);
                BlockPos pos = new BlockPos(x, y = (double)Integer.parseInt(args[1]), z = (double)Integer.parseInt(args[2]));
                //RenderUtil.fairySoulESP(pos, loopListfound.contains(loc) ? Color.red.getRGB() : Color.cyan.getRGB());
                RenderUtil.blockESPBox(pos, 255, 255, 0, 1);
            }
        } 
	}
    
    private static void fixLists() {
        if (FairySoulUtil.currentSetting == 0) {
            loopList = FairySoulUtil.hub;
            loopListfound = FairySoulUtil.hubfound;
        } else if (FairySoulUtil.currentSetting == 1) {
            loopList = FairySoulUtil.mine;
            loopListfound = FairySoulUtil.minefound;
        } else if (FairySoulUtil.currentSetting == 2) {
            loopList = FairySoulUtil.caverns;
            loopListfound = FairySoulUtil.cavernsfound;
        } else if (FairySoulUtil.currentSetting == 3) {
            loopList = FairySoulUtil.islands;
            loopListfound = FairySoulUtil.islandsfound;
        } else if (FairySoulUtil.currentSetting == 4) {
            loopList = FairySoulUtil.barn;
            loopListfound = FairySoulUtil.barnfound;
        } else if (FairySoulUtil.currentSetting == 5) {
            loopList = FairySoulUtil.desert;
            loopListfound = FairySoulUtil.desertfound;
        } else if (FairySoulUtil.currentSetting == 6) {
            loopList = FairySoulUtil.den;
            loopListfound = FairySoulUtil.denfound;
        } else if (FairySoulUtil.currentSetting == 7) {
            loopList = FairySoulUtil.fortress;
            loopListfound = FairySoulUtil.fortressfound;
        } else if (FairySoulUtil.currentSetting == 8) {
            loopList = FairySoulUtil.end;
            loopListfound = FairySoulUtil.endfound;
        } else if (FairySoulUtil.currentSetting == 9) {
            loopList = FairySoulUtil.darwen;
            loopListfound = FairySoulUtil.darwenfound;
        } else if (FairySoulUtil.currentSetting == 10) {
            loopList = FairySoulUtil.dungeons;
            loopListfound = FairySoulUtil.dungeonsfound;
        }
    }

    private static void addToFound(String loc) {
        if (FairySoulUtil.currentSetting == 0) {
            FairySoulUtil.hubfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 1) {
            FairySoulUtil.minefound.add(loc);
        } else if (FairySoulUtil.currentSetting == 2) {
            FairySoulUtil.cavernsfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 3) {
            FairySoulUtil.islandsfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 4) {
            FairySoulUtil.barnfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 5) {
            FairySoulUtil.desertfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 6) {
            FairySoulUtil.denfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 7) {
            FairySoulUtil.fortressfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 8) {
            FairySoulUtil.endfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 9) {
            FairySoulUtil.darwenfound.add(loc);
        } else if (FairySoulUtil.currentSetting == 10) {
            FairySoulUtil.dungeonsfound.add(loc);
        }
    }
}
