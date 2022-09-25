package cn.KiesPro.module.combat;

import java.util.ArrayList;
import java.util.List;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventUpdate;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;


public class AntiBot extends Module {
	//mode
    private ArrayList<String> sites = new ArrayList<String>();
    
    public int count;
    
	public AntiBot() {
		super("AntiBot", "Prevent Bot(only in hypixel)", Category.COMBAT);
        sites.add("Hypixel");

        Client.instance.settingsManager.rSetting(new Setting("Mode", this, "Hypixel",sites));
	}
	
	@EventTarget
	public void onTick(EventUpdate event) {
		
		String mode = Client.instance.settingsManager.getSettingByName(this,"Mode").getValString();
		
        if (mode == "Hypixel") {
            for (final EntityPlayer entityPlayer : mc.theWorld.playerEntities) {
                if (entityPlayer != mc.thePlayer && entityPlayer != null && !getTabPlayerList().contains(entityPlayer) && !entityPlayer.getDisplayName().getFormattedText().toLowerCase().contains("[npc") && entityPlayer.getDisplayName().getFormattedText().startsWith("§") && entityPlayer.isEntityAlive() && !isHypixelNPC(entityPlayer)) {
                    mc.theWorld.removeEntity((Entity)entityPlayer);
                    ++this.count;
                }
            }
        }

	}
	
	
    public static List<EntityPlayer> getTabPlayerList() {
        ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
        List<NetworkPlayerInfo> players = GuiPlayerTabOverlay.field_175252_a.sortedCopy(mc.getNetHandler().getPlayerInfoMap());

        for (NetworkPlayerInfo o : players) {
            if (o != null) {
                list.add(mc.theWorld.getPlayerEntityByName(o.getGameProfile().getName()));
            }
        }
        return list;
    }
    
	
	public static boolean isHypixelNPC(final EntityPlayer entityPlayer) {
        final String substring = entityPlayer.getDisplayName().getFormattedText().substring(2);
        entityPlayer.getCustomNameTag();
        return (!substring.startsWith("§") && substring.endsWith("§r")) || substring.contains("[NPC]");
    }
	
}
