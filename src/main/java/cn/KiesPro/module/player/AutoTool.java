package cn.KiesPro.module.player;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.raven.RavenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class AutoTool extends Module {

	private Setting hotkeyBack;
	private Setting Sword;
	private Setting doDelay;
	private Setting minDelay;
	private Setting maxDelay;
	public static int previousSlot;
	public static boolean justFinishedMining, mining;

	public AutoTool() {
		super("AutoTool", "Auto Set best Tools", Category.PLAYER);

		this.registerSetting(hotkeyBack = new Setting("Hotkey back", this, true));
		this.registerSetting(Sword = new Setting("Sword", this, true));
		this.registerSetting(doDelay = new Setting("Random delay", this, true));
		this.registerSetting(minDelay = new Setting("Min Delay", this, 25, 0, 600, true));
		this.registerSetting(maxDelay = new Setting("Max Delay", this, 100, 0, 600, true));
	}

	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {

		if (!RavenUtils.isPlayerInGame() || mc.currentScreen != null)
			return;

		double max = Client.instance.settingsManager.getSettingByName(this, "Max Delay").getValDouble();
		double min = Client.instance.settingsManager.getSettingByName(this, "Min Delay").getValDouble();
		
		if (min >= max) {
			Client.instance.settingsManager.getSettingByName(this, "Max Delay").setValDouble(min + 1);
			min = Client.instance.settingsManager.getSettingByName(this, "Max Delay").getValDouble();
		}
		
		if (Mouse.isButtonDown(0)) {

			BlockPos lookingAtBlock = mc.objectMouseOver.getBlockPos();
			if (lookingAtBlock != null) {
				Block stateBlock = mc.theWorld.getBlockState(lookingAtBlock).getBlock();
				if (stateBlock != Blocks.air && !(stateBlock instanceof BlockLiquid) && stateBlock instanceof Block) {
					if (!mining) {
						previousSlot = RavenUtils.getCurrentPlayerSlot();
						mining = true;
					}
					int index = -1;
					double speed = 1;

					for (int slot = 0; slot <= 8; slot++) {
						ItemStack itemInSlot = mc.thePlayer.inventory.getStackInSlot(slot);
						if (itemInSlot != null && itemInSlot.getItem() instanceof ItemTool) {
							BlockPos p = mc.objectMouseOver.getBlockPos();
							Block bl = mc.theWorld.getBlockState(p).getBlock();

							if (itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
								speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
								index = slot;
							}
						} else if (itemInSlot != null && itemInSlot.getItem() instanceof ItemShears) {
							BlockPos p = mc.objectMouseOver.getBlockPos();
							Block bl = mc.theWorld.getBlockState(p).getBlock();

							if (itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState()) > speed) {
								speed = itemInSlot.getItem().getDigSpeed(itemInSlot, bl.getDefaultState());
								index = slot;
							}
						}
					}

					if (index == -1 || speed <= 1.1 || speed == 0) {
					} else {
						RavenUtils.hotkeyToSlot(index);
					}
				}
			} 
		} else {
			if (mining)
				finishMining();
		}
	}

	public void finishMining() {
		if (hotkeyBack.isEnabled()) {
			RavenUtils.hotkeyToSlot(previousSlot);
		}
		justFinishedMining = false;
		mining = false;
	}

}
