package cn.KiesPro.module.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cn.KiesPro.event.eventapi.EventTarget;
import cn.KiesPro.event.events.EventRender3D;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class NameTags extends Module {
	
    
	public NameTags() {
		super("NameTags", "透视名字", Category.RENDER);
//		Client.instance.settingsManager.rSetting(new Setting("scale", this, 1.0, 0.1, 5.0, false));
	}
	
	@EventTarget
    public void onRender3D(EventRender3D e) {
        for (Object o : mc.theWorld.playerEntities) {
            EntityPlayer player = (EntityPlayer) o;
            if (player.isInvisible())
                continue;

            double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * e.getPartialTicks() - mc.getRenderManager().renderPosX;
            double y2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * e.getPartialTicks() - mc.getRenderManager().renderPosY;
            double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * e.getPartialTicks() - mc.getRenderManager().renderPosZ;

            renderNametag(player, x, y2, z);
        }
    }
	
    public static void renderNametag(EntityPlayer player, double x, double y2, double z) {
        double health;
        double percentage;
        String entityName = player.getDisplayName().getFormattedText();
        if (player == mc.thePlayer) {
            return;
        }

        if (player.isDead) {
            return;
        }

        if (player.capabilities.isFlying) {
            entityName = "\u00a7a[F] \u00a7r" + entityName;
        }

        if (player.capabilities.isCreativeMode) {
            entityName = "\u00148[C] \u00a7r" + entityName;
        }

        if (player.getDistanceToEntity(mc.thePlayer) >= 64.0f) {
            entityName = "\u00a7r" + entityName;
        }

        String healthColor = (percentage = 100.0
                * ((health = (int) player.getHealth()) / (player.getMaxHealth() / 2.0f))) > 75.0 ? "2"
                : percentage > 50.0 ? "e" : percentage > 25.0 ? "6" : "4";
        String healthDisplay = String.valueOf(health);
        entityName = String.format("%s \u00a7%s %s", entityName, healthColor, healthDisplay);
        float distance = mc.thePlayer.getDistanceToEntity(player);
        float var13 = (Math.max(distance / 5.0f, 2.0f)) * 0.95f;
        float var14 = 0.016666668f * var13;
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(x + 0.0, y2 + player.height + 0.5, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        if (mc.gameSettings.thirdPersonView == 2) {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, -1.0f, 0.0f, 0.0f);
        } else {
            GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        }
        GlStateManager.scale(-var14, -var14, var14);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int var15 = 0;
        if (player.isSneaking()) {
            var15 += 4;
        }
        if ((var15 -= (int) (distance / 5.0f)) < -8) {
            var15 = -8;
        }
        GlStateManager.disableTexture2D();
        int var16 = mc.fontRendererObj.getStringWidth(entityName) / 2;
        GlStateManager.enableTexture2D();
        mc.fontRendererObj.drawString(entityName, -var16, var15, -1, true);
        ArrayList<ItemStack> items = new ArrayList<>();
        if (player.getCurrentEquippedItem() != null) {
            items.add(player.getCurrentEquippedItem());
        }
        for (int index = 3; index >= 0; --index) {
            ItemStack stack = player.inventory.armorInventory[index];
            if (stack == null) {
                continue;
            }
            items.add(stack);
        }
        int offset = var16 - (items.size() - 1) * 9 - 9;
        int xPos = 0;
        for (ItemStack stack2 : items) {
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            mc.getRenderItem().zLevel = -100.0f;

            mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, stack2, -var16 + offset + xPos, var15 - 20);
            mc.getRenderItem().zLevel = 0.0f;
            RenderHelper.disableStandardItemLighting();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.disableLighting();
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            if (stack2.getItem() == Items.golden_apple && stack2.hasEffect()) {
                mc.fontRendererObj.drawStringWithShadow("god", (-var16 + offset + xPos) * 2,
                        (var15 - 20) * 2, -65536);
            } else {
                NBTTagList enchants = stack2.getEnchantmentTagList();
                if (enchants != null) {
                    int encY = 0;
                    Enchantment[] important = new Enchantment[]{Enchantment.protection,
                            Enchantment.unbreaking, Enchantment.sharpness, Enchantment.fireAspect,
                            Enchantment.efficiency, Enchantment.featherFalling, Enchantment.power,
                            Enchantment.flame, Enchantment.punch, Enchantment.fortune, Enchantment.infinity,
                            Enchantment.thorns};
                    if (enchants.tagCount() >= 6) {
                        mc.fontRendererObj.drawStringWithShadow("god", (-var16 + offset + xPos) * 2,
                                (var15 - 20) * 2, -65536);
                    } else {
                        block2:
                        for (int index2 = 0; index2 < enchants.tagCount(); ++index2) {
                            short id = enchants.getCompoundTagAt(index2).getShort("id");
                            short level = enchants.getCompoundTagAt(index2).getShort("lvl");
                            Enchantment enc = Enchantment.getEnchantmentById(id);
                            if (enc == null) {
                                continue;
                            }
                            for (Enchantment importantEnchantment : important) {
                                if (enc != importantEnchantment) {
                                    continue;
                                }
                                String encName = enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                                encName = level > 99
                                        ? String.valueOf(String.valueOf(encName)) + "99+"
                                        : String.valueOf(String.valueOf(String.valueOf(encName))) + level;
                                mc.fontRendererObj.drawStringWithShadow(encName,
                                        (-var16 + offset + xPos) * 2, (var15 - 20 + encY) * 2, -5592406);
                                encY += 5;
                                continue block2;
                            }
                        }
                    }
                }
            }
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
            xPos += 18;
        }
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }
}
