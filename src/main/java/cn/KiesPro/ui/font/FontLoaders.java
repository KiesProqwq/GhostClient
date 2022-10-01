package cn.KiesPro.ui.font;

import java.io.InputStream;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import java.awt.Font;

public abstract class FontLoaders
{
    public static CFontRenderer taileb15;
    public static CFontRenderer robotom15;

    private static Font getTaileb(final int size) {
        Font font;
        try {
            final InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("KiesPro/taileb.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
    
    private static Font getRoboto(final int size) {
        Font font;
        try {
            final InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("KiesPro/robotoMedium.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    static {
        FontLoaders.taileb15 = new CFontRenderer(getTaileb(15), true, true);
        FontLoaders.robotom15 = new CFontRenderer(getRoboto(15), true, true);
    }
}
