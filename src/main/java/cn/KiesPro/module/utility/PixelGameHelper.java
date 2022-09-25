package cn.KiesPro.module.utility;

import com.mojang.realmsclient.gui.ChatFormatting;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PixelGameHelper extends Module {

	public PixelGameHelper() {
		super("PixelGameHelper", "PixelGame显示方块ID", Category.Utility);
	}
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent e) {
        if (mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
        	
            final BlockPos pos = mc.objectMouseOver.getBlockPos();
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            final ScaledResolution sr = new ScaledResolution(mc);
            final int id = Block.getIdFromBlock(block);
            
            final String s = String.valueOf(String.valueOf(ChatFormatting.GOLD + block.getLocalizedName())) + " ID" + " :" + ChatFormatting.WHITE + id;
            
            final int x = sr.getScaledWidth() / 2 + 6;
            final int y = sr.getScaledHeight() / 2 - 1;
            mc.fontRendererObj.drawStringWithShadow(s, x + 4.0f, y - 2.65f, -9868951);
        }
	}

}
