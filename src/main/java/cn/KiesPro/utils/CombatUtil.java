package cn.KiesPro.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import cn.KiesPro.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;

public class CombatUtil {
	
	protected static Minecraft mc = Minecraft.getMinecraft();
	
    private static String[] lIIllIIll;
    private static String[] lIIllllII;
    
    public static boolean canTarget(Entity entity, boolean flag) {
        if (entity != null && entity != mc.thePlayer) {
            EntityPlayer entityplayer = null;
            EntityLivingBase entitylivingbase = null;

            if (entity instanceof EntityPlayer) {
                entityplayer = (EntityPlayer) entity;
            }

            if (entity instanceof EntityLivingBase) {
                entitylivingbase = (EntityLivingBase) entity;
            }

            boolean flag1 = Client.instance.moduleManager.getModule(CombatUtil.lIIllIIll[0]).isToggled();
            boolean flag2 = Client.instance.moduleManager.getModule(CombatUtil.lIIllIIll[1]).isToggled();
            boolean flag3 = Client.instance.moduleManager.getModule(CombatUtil.lIIllIIll[2]).isToggled();
            boolean flag4 = Client.instance.moduleManager.getModule(CombatUtil.lIIllIIll[3]).isToggled();
            boolean flag5 = Client.instance.moduleManager.getModule(CombatUtil.lIIllIIll[4]).isToggled();
            boolean flag6 = Client.instance.moduleManager.getModule(CombatUtil.lIIllIIll[5]).isToggled();
            boolean flag7 = Client.instance.moduleManager.getModule(CombatUtil.lIIllIIll[6]).isToggled() && flag;
            boolean flag8 = false;

            if (flag7 && isTeam(mc.thePlayer, entity)) {
                flag8 = true;
            }

            boolean flag9 = true;

            if (entity.isInvisible() && !flag4) {
                flag9 = false;
            }

            boolean flag10 = false;

            if (Client.instance.moduleManager.getModule(CombatUtil.lIIllIIll[7]).isToggled()) {
                flag10 = true;
            }

            return !(entity instanceof EntityArmorStand) && !flag10 && flag9 && (entity instanceof EntityPlayer && flag1 && !flag8 && (!flag || !isNaked(entitylivingbase) || !flag6) || entity instanceof EntityAnimal && flag2 || entity instanceof EntityMob && flag3 || entity instanceof EntityLivingBase && flag5 && !(entity instanceof EntityMob) && !(entity instanceof EntityAnimal) && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && entitylivingbase.isEntityAlive());
        } else {
            return false;
        }
    }

    public static boolean isTeam(EntityPlayer entityplayer, Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).getTeam() != null && entityplayer.getTeam() != null) {
            Character character = Character.valueOf(entity.getDisplayName().getFormattedText().charAt(3));
            Character character1 = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(3));
            Character character2 = Character.valueOf(entity.getDisplayName().getFormattedText().charAt(2));
            Character character3 = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(2));
            boolean flag = false;

            if (character.equals(character1) && character2.equals(character3)) {
                flag = true;
            } else {
                Character character4 = Character.valueOf(entity.getDisplayName().getFormattedText().charAt(1));
                Character character5 = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(1));
                Character character6 = Character.valueOf(entity.getDisplayName().getFormattedText().charAt(0));
                Character character7 = Character.valueOf(entityplayer.getDisplayName().getFormattedText().charAt(0));

                if (character4.equals(character5) && Character.isDigit(0) && character6.equals(character7)) {
                    flag = true;
                }
            }

            return flag;
        } else {
            return true;
        }
    }

    private static boolean isNaked(EntityLivingBase entitylivingbase) {
        return entitylivingbase.getTotalArmorValue() == 0;
    }

    static {
        lIIIlIIllIl();
        lIIIlIIlIll();
    }

    private static void lIIIlIIlIll() {
        lIIllIIll = new String[8];
        CombatUtil.lIIllIIll[0] = lIIIlIIIlll(CombatUtil.lIIllllII[0], CombatUtil.lIIllllII[1]);
        CombatUtil.lIIllIIll[1] = lIIIlIIlIII(CombatUtil.lIIllllII[2], CombatUtil.lIIllllII[3]);
        CombatUtil.lIIllIIll[2] = lIIIlIIlIIl(CombatUtil.lIIllllII[4], CombatUtil.lIIllllII[5]);
        CombatUtil.lIIllIIll[3] = lIIIlIIlIIl(CombatUtil.lIIllllII[6], CombatUtil.lIIllllII[7]);
        CombatUtil.lIIllIIll[4] = lIIIlIIlIII(CombatUtil.lIIllllII[8], CombatUtil.lIIllllII[9]);
        CombatUtil.lIIllIIll[5] = lIIIlIIlIII(CombatUtil.lIIllllII[10], CombatUtil.lIIllllII[11]);
        CombatUtil.lIIllIIll[6] = lIIIlIIIlll(CombatUtil.lIIllllII[12], CombatUtil.lIIllllII[13]);
        CombatUtil.lIIllIIll[7] = lIIIlIIlIII(CombatUtil.lIIllllII[14], CombatUtil.lIIllllII[15]);
        CombatUtil.lIIllllII = null;
    }

    private static void lIIIlIIllIl() {
        String s = (new Exception()).getStackTrace()[0].getFileName();

        CombatUtil.lIIllllII = s.substring(s.indexOf("ä") + 1, s.lastIndexOf("ü")).split("ö");
    }

    private static String lIIIlIIIlll(String s, String s1) {
        s = new String(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        StringBuilder stringbuilder = new StringBuilder();
        char[] achar = s1.toCharArray();
        int i = 0;
        char[] achar1 = s.toCharArray();
        int j = achar1.length;

        for (int k = 0; k < j; ++k) {
            char c0 = achar1[k];

            stringbuilder.append((char) (c0 ^ achar[i % achar.length]));
            ++i;
        }

        return String.valueOf(stringbuilder);
    }

    private static String lIIIlIIlIIl(String s, String s1) {
        try {
            SecretKeySpec secretkeyspec = new SecretKeySpec(Arrays.copyOf(MessageDigest.getInstance("MD5").digest(s1.getBytes(StandardCharsets.UTF_8)), 8), "DES");
            Cipher cipher = Cipher.getInstance("DES");

            cipher.init(2, secretkeyspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static String lIIIlIIlIII(String s, String s1) {
        try {
            SecretKeySpec secretkeyspec = new SecretKeySpec(MessageDigest.getInstance("MD5").digest(s1.getBytes(StandardCharsets.UTF_8)), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");

            cipher.init(2, secretkeyspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(s.getBytes(StandardCharsets.UTF_8))), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
