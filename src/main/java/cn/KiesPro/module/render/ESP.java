package cn.KiesPro.module.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.HUDUtils;
import cn.KiesPro.utils.color.ColorUtils;
import cn.KiesPro.utils.raven.RavenUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ESP extends Module {

    private ArrayList<String> sites = new ArrayList<String>();

    public static Setting Players,Inv,Mobs,Animals;
    public Setting RainBow;
    
    public ESP() {
        super("ESP", "Renders players in a dif way(透视666)", Category.RENDER);

        sites.add("2D");
        sites.add("Arrow");
        sites.add("Box");
        sites.add("Health");
        sites.add("Ring");
        sites.add("Shaded");

        Client.instance.settingsManager.rSetting(new Setting("Red", this, 0, 0, 255, true));
        Client.instance.settingsManager.rSetting(new Setting("Blue", this, 0, 0, 255, true));
        Client.instance.settingsManager.rSetting(new Setting("Green", this, 0, 0, 255, true));
        Client.instance.settingsManager.rSetting(RainBow = new Setting("RainBow", this, true));
        Client.instance.settingsManager.rSetting(Players = new Setting("Players", this, true));
        Client.instance.settingsManager.rSetting(Inv = new Setting("Inv", this, true));
        Client.instance.settingsManager.rSetting(Mobs = new Setting("Mobs", this, true));
        Client.instance.settingsManager.rSetting(Animals = new Setting("Animals", this, true));
        Client.instance.settingsManager.rSetting(new Setting("Types", this, "2D",sites));

    }


    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if(!RavenUtils.currentScreenMinecraft()){
            return;
        }

        int Red = (int)Client.instance.settingsManager.getSettingByName(this,"Red").getValDouble();
        int Blue = (int)Client.instance.settingsManager.getSettingByName(this,"Blue").getValDouble();
        int Green = (int)Client.instance.settingsManager.getSettingByName(this,"Green").getValDouble();

        String Type = Client.instance.settingsManager.getSettingByName(this,"Types").getValString();

        int rgb = RainBow.isEnabled()? ColorUtils.rainbow().getRGB() :(new Color((int)Red, (int)Blue, (int)Green)).getRGB();
        Iterator var3;

        var3 = mc.theWorld.loadedEntityList.iterator();

        while(true) {
            Entity en;
            do {
                do {
                    do {
                        if (!var3.hasNext()) {
                            return;
                        }

                        en = (Entity)var3.next();
                    } while(en == mc.thePlayer);
                } while(en.isDead != false);
            } while(!Inv.isEnabled() && en.isInvisible());

            //if (!AntiBot.bots(en)) {

                if (en instanceof EntityPlayer){
                    if (Players.isEnabled()) {
                        if (Type == "2D"){
                            HUDUtils.ee(en, 3, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Arrow"){
                            HUDUtils.ee(en, 5, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Box"){
                            HUDUtils.ee(en, 1, 0.5D, 1D, rgb, false);
                        }
                        if (Type == "Health"){
                            HUDUtils.ee(en, 4, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Ring"){
                            HUDUtils.ee(en, 6, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Shaded"){
                            HUDUtils.ee(en, 2, 0.5D, 1D, rgb, true);
                        }
                    }
                }
                if (en instanceof EntityAnimal){
                    if (Animals.isEnabled()) {
                        if (Type == "2D"){
                            HUDUtils.ee(en, 3, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Arrow"){
                            HUDUtils.ee(en, 5, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Box"){
                            HUDUtils.ee(en, 1, 0.5D, 1D, rgb, false);
                        }
                        if (Type == "Health"){
                            HUDUtils.ee(en, 4, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Ring"){
                            HUDUtils.ee(en, 6, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Shaded"){
                            HUDUtils.ee(en, 2, 0.5D, 1D, rgb, true);
                        }
                    }
                }
                if (en instanceof EntityMob){
                    if (Mobs.isEnabled()) {
                        if (Type == "2D"){
                            HUDUtils.ee(en, 3, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Arrow"){
                            HUDUtils.ee(en, 5, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Box"){
                            HUDUtils.ee(en, 1, 0.5D, 1D, rgb, false);
                        }
                        if (Type == "Health"){
                            HUDUtils.ee(en, 4, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Ring"){
                            HUDUtils.ee(en, 6, 0.5D, 1D, rgb, true);
                        }
                        if (Type == "Shaded"){
                            HUDUtils.ee(en, 2, 0.5D, 1D, rgb, true);
                        }
                    }
                }
            }
        }
    }
