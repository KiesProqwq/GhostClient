package cn.KiesPro.module.render;

import java.awt.Color;

import com.mojang.realmsclient.gui.ChatFormatting;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.module.combat.AntiBot;
import cn.KiesPro.utils.Nameplate;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NameTags extends Module {

	public NameTags() {
		super("NameTags", "透视名字666", Category.RENDER);
		// TODO 自动生成的构造函数存根
	}
	
    @SubscribeEvent
    public void onPreRender(RenderPlayerEvent.Pre event) {
        
        Scoreboard sb = event.entityPlayer.getWorldScoreboard();
        ScoreObjective sbObj = sb.getObjectiveInDisplaySlot(2);
        if (sbObj != null && !event.entityPlayer.getDisplayNameString().equals(mc.thePlayer.getDisplayNameString()) && event.entityPlayer.getDistanceSqToEntity((Entity)mc.thePlayer) < 100.0) {
        }
        if(event.entityPlayer.isDead)
            return;
        
        //bot给他remove了
        
        //if(AntiBot.isServerBot(event.entityPlayer)) return;
        
        if(event.entityPlayer.isInvisible())
            return;
        if (!event.entityPlayer.getDisplayName().equals(mc.thePlayer.getDisplayName())) {
            ChatFormatting Format = ChatFormatting.WHITE;
            if(event.entityPlayer.getHealth() > 15){
                Format = ChatFormatting.WHITE;
            } else if(event.entityPlayer.getHealth() > 8 && event.entityPlayer.getHealth() < 15){
                Format = ChatFormatting.YELLOW;
            } else {
                Format = ChatFormatting.RED;
            }
            Nameplate np = new Nameplate(event.entityPlayer.getDisplayNameString(), event.x, event.y, event.z, event.entityLiving);
            np.renderNewPlate(new Color(150,150,150));
        }
    }

}
