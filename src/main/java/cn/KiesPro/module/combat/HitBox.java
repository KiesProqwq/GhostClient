package cn.KiesPro.module.combat;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventUpdate;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.raven.RavenUtils;

import java.util.List;

public class HitBox extends Module {

    public static Setting expand;
    public static Setting extra;
    public static Setting extraV;

    public HitBox() {
        super("HitBox", "咱就是说这个功能开了直接关闭无法直接使游戏变回去，所以你必须在关闭她时将Expand拉到最小", Category.COMBAT);
        expand = new Setting("Expand",this, 0.1, 0.0, 1.0, false);
        extra = new Setting("Extra",this,false);
        extraV = new Setting("ExtraExpand",this, 0.0, 0.0, 15.0, false);
        this.registerSetting(expand);
        this.registerSetting(extraV);
        this.registerSetting(extra);
    }
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(!RavenUtils.currentScreenMinecraft())
            return;

        List loadedEntityList = mc.theWorld.loadedEntityList;
        for (int i = 0; i < loadedEntityList.size(); ++i) {
            Entity e = (Entity)loadedEntityList.get(i);
            if (e == mc.thePlayer || !e.canBeCollidedWith()) continue;
            e.width = (float)(extra.isEnabled()?0.6 + expand.getValDouble()+extraV.getValDouble():0.6 + expand.getValDouble()//expand.getValue()
            );
        }
    }

    public Entity entity() {
        Entity e = null;
        if (mc.thePlayer.worldObj != null) {
            for (Object o : mc.theWorld.loadedEntityList) {
                e = (Entity) o;
            }
        }
        return e;
    }
}
