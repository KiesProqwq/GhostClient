package cn.KiesPro.module.pvp;

import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class ArmorHUD extends Module {
	
    private int offHandHeldItemCount;
    private int armourCompress;
    private int armourSpacing;
    
	public ArmorHUD() {
		super("ArmorHUD", "Show your Armor", Category.RENDER);
	}
	
	
    public static void renderArmorStatus(ScaledResolution sr, int pos, ItemStack itemStack) {
        if (itemStack != null) {
            final boolean posX = false;
            final boolean posY = false;
            final int posXAdd = -16 * pos + 48;
            //GlStateManager.func_179094_E();
            //RenderHelper.func_74520_c();
            //Minecraft.func_71410_x().func_175599_af().func_180450_b(itemStack, sr.func_78326_a() / 2 + 20 + posXAdd, sr.func_78328_b() - 55);
            //GlStateManager.func_179121_F();
        }
    }
}
