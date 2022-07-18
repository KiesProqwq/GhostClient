package cn.KiesPro.module.render;
/*
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.BlockFound;
import cn.KiesPro.utils.RenderUtil;
import cn.KiesPro.utils.TimerUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Xray extends Module {
	
    public int range;
    public boolean iron;
    public boolean gold;
    public boolean diamond;
    public boolean emerald;
    public boolean lapis;
    public boolean redstone;
    public boolean coal;
    public boolean spawner;
    public List ores;
    public List blocksFound;
    private TimerUtils updateTimer = new TimerUtils();
	
	public Xray() {
		super("Xray", "Fine Block", Category.RENDER);
		Client.instance.settingsManager.rSetting(new Setting("Range", this, 50, 5, 200, true));
		Client.instance.settingsManager.rSetting(new Setting("UpdateTime", this, 4000, 1000, 10000, false));
		Client.instance.settingsManager.rSetting(new Setting("Iron", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Coal", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Gold", this, true));
		Client.instance.settingsManager.rSetting(new Setting("Diamond", this, true));
		Client.instance.settingsManager.rSetting(new Setting("Redstone", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Emerald", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Lapis", this, false));
		Client.instance.settingsManager.rSetting(new Setting("Spawner", this, false));
		
		(this.blocksFound = new ArrayList()).clear();
        (this.ores = new ArrayList()).add(Blocks.iron_ore);
        this.ores.add(Blocks.gold_ore);
        this.ores.add(Blocks.diamond_ore);
        this.ores.add(Blocks.emerald_ore);
        this.ores.add(Blocks.lapis_ore);
        this.ores.add(Blocks.redstone_ore);
        this.ores.add(Blocks.coal_ore);
        this.ores.add(Blocks.mob_spawner);
	}
	
	public void updateVal() {
		this.range = (int)Client.instance.settingsManager.getSettingByName(this, "Range").getValDouble();
		this.iron = Client.instance.settingsManager.getSettingByName(this, "Iron").getValBoolean();
		this.gold = Client.instance.settingsManager.getSettingByName(this, "Gold").getValBoolean();
		this.diamond = Client.instance.settingsManager.getSettingByName(this, "Diamond").getValBoolean();
		this.redstone = Client.instance.settingsManager.getSettingByName(this, "Redstone").getValBoolean();
		this.coal = Client.instance.settingsManager.getSettingByName(this, "Coal").getValBoolean();
		this.emerald = Client.instance.settingsManager.getSettingByName(this, "Emerald").getValBoolean();
		this.lapis = Client.instance.settingsManager.getSettingByName(this, "Lapis").getValBoolean();
		this.spawner = Client.instance.settingsManager.getSettingByName(this, "Spawner").getValBoolean();
	}
	
    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent rwle) {
        if (this.updateTimer.hasReached(Client.instance.settingsManager.getSettingByName(this, "UpdateTime").getValDouble())) {
            this.updateBlocks();
            this.updateTimer.reset();
        }
        
        if (this.iron || this.gold || this.coal || this.diamond || this.redstone || this.emerald || this.lapis || this.spawner) {
            for (int i = 0; i < this.blocksFound.size(); ++i) {
                this.draw((BlockFound) this.blocksFound.get(i));
            }
        }
    }
    
    private void updateBlocks() {
        this.blocksFound.clear();
        BlockPos blockpos = mc.thePlayer.getPosition();
        int i = this.range;

        for (int j = blockpos.getX() - i; j <= blockpos.getX() + i; ++j) {
            for (int k = blockpos.getZ() - i; k < blockpos.getZ() + i; ++k) {
                for (int l = blockpos.getY() - i; l < blockpos.getY() + i; ++l) {
                    Block block = mc.theWorld.getBlockState(new BlockPos(j, l, k)).getBlock();

                    if (this.ores.contains(block) && (this.iron || !block.equals(Blocks.iron_ore)) && (this.gold || !block.equals(Blocks.gold_ore)) && (this.diamond || !block.equals(Blocks.diamond_ore)) && (this.emerald || !block.equals(Blocks.emerald_ore)) && (this.lapis || !block.equals(Blocks.lapis_ore)) && (this.redstone || !block.equals(Blocks.redstone_ore)) && (this.coal || !block.equals(Blocks.coal_ore)) && (this.spawner || !block.equals(Blocks.mob_spawner))) {
                        this.blocksFound.add(new BlockFound(new BlockPos(j, l, k), block, this.color(block)));
                    }
                }
            }
        }
    }
    
    private void draw(BlockFound blockfound) {
        RenderUtil.blockESPBox(blockfound.pos, (float) blockfound.color.getRed(), (float) blockfound.color.getGreen(), (float) blockfound.color.getBlue(), 1.0F);
    }
    
    private Color color(Block block) {
        short short0 = 0;
        short short1 = 0;
        short short2 = 0;

        if (block.equals(Blocks.iron_ore)) {
            short0 = 255;
            short1 = 255;
            short2 = 255;
        } else if (block.equals(Blocks.gold_ore)) {
            short0 = 255;
            short1 = 255;
        } else if (block.equals(Blocks.diamond_ore)) {
            short1 = 220;
            short2 = 255;
        } else if (block.equals(Blocks.emerald_ore)) {
            short0 = 35;
            short1 = 255;
        } else if (block.equals(Blocks.lapis_ore)) {
            short1 = 50;
            short2 = 255;
        } else if (block.equals(Blocks.redstone_ore)) {
            short0 = 255;
        } else if (block.equals(Blocks.mob_spawner)) {
            short0 = 30;
            short2 = 135;
        }
        return new Color(short0, short1, short2);
    }
}*/
