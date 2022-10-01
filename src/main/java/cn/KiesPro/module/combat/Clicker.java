package cn.KiesPro.module.combat;

import org.lwjgl.input.Mouse;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.MathUtils;
import cn.KiesPro.utils.TimerUtils;
import cn.KiesPro.utils.raven.RavenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Clicker extends Module {

    private static long lastMS = 0L;
    public static Setting leftMinCPS, rightMinCPS, breakBlocksMin, breakBlocksMax;
    public static Setting leftMaxCPS, rightMaxCPS;
    public static Setting jitterLeft;
    public static Setting jitterRight;
    public static Setting weaponOnly;
    public static Setting breakBlocks;
    public static Setting onlyBlocks;
    public static Setting preferFastPlace;
    public static Setting noBlockSword;
    public static Setting leftClick;
    public static Setting rightClick;
    public static Setting inventoryFill;
    public static Setting allowEat, allowBow;
    public static Setting rightClickDelay;
    public static Setting clickEvent, clickTimings;

    private Random rand = null;
    private Method playerMouseInput;
    private long leftDownTime, righti;
    private long leftUpTime, rightj;
    private long leftk, rightk;
    private long leftl, rightl;
    private double leftm, rightm;
    private boolean leftn, rightn;
    private boolean breakHeld;
    private boolean watingForBreakTimeout;
    private double breakBlockFinishWaitTime;
    private long lastClick;
    private long leftHold, rightHold;
    private boolean rightClickWaiting;
    private double rightClickWaitStartTime;
    private boolean allowedClick;
    public static boolean autoClickerEnabled, breakTimeDone;
    public static int clickFinder;
    public static int clickCount;

    ArrayList<String> sites = new ArrayList<String>();
    ArrayList<String> sites2 = new ArrayList<String>();
    public Clicker() {
        super("Clicker","qwq click for u!", Category.COMBAT);

        sites.add("ClassLoader");
        sites.add("Normal");

        sites2.add("RENDER");
        sites2.add("TICK");

        this.registerSetting(leftClick = new Setting("Left click",this , true));
        this.registerSetting(leftMinCPS = new Setting("Left Min CPS",this , 9.0D, 1.0D, 60.0D, false));
        this.registerSetting(leftMaxCPS = new Setting("Left Max CPS", this ,13.0D, 1.0D, 60.0D, false));
        this.registerSetting(rightClick = new Setting("Right click", this ,false));
        this.registerSetting(rightMinCPS = new Setting("Right Min CPS", this ,12.0D, 1.0D, 60.0D, false));
        this.registerSetting(rightMaxCPS = new Setting("Right Max CPS", this ,16.0D, 1.0D, 60.0D, false));
        this.registerSetting(inventoryFill = new Setting("Inventory fill", this ,false));
        this.registerSetting(weaponOnly = new Setting("Weapon only", this ,false));
        this.registerSetting(noBlockSword = new Setting("noBlockSword", this ,true));
        this.registerSetting(onlyBlocks = new Setting("onlyBlocks", this ,false));
        this.registerSetting(preferFastPlace = new Setting("Prefer FastPlace", this ,false));
        this.registerSetting(breakBlocks = new Setting("Break Blocks", this ,false));
        this.registerSetting(breakBlocksMin = new Setting("Break MinDelay", this ,20.0D, 0.0D, 1000.0D, true));
        this.registerSetting(breakBlocksMax = new Setting("Break MaxDelay", this ,50.0D, 0.0D, 1000.0D, true));
        this.registerSetting(allowEat = new Setting("Allow eat", this ,true));
        this.registerSetting(allowBow = new Setting("Allow bow", this ,true));
        this.registerSetting(jitterLeft = new Setting("JitterLeft", this ,0.0D, 0.0D, 3.0D, false));
        this.registerSetting(jitterRight = new Setting("JitterRight", this ,0.0D, 0.0D, 3.0D, false));
        this.registerSetting(rightClickDelay = new Setting("RightClick Delay",this , 85D, 0D, 500D, true));
        this.registerSetting(clickTimings = new Setting("ClickStyle",this ,"Normal",sites));

        this.registerSetting(clickEvent = new Setting("Event", this,"TICK",sites2));

        try {
            this.playerMouseInput = GuiScreen.class.getDeclaredMethod("func_73864_a", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        } catch (Exception var4) {
            try {
                this.playerMouseInput = GuiScreen.class.getDeclaredMethod("mouseClicked", Integer.TYPE, Integer.TYPE, Integer.TYPE);
            } catch (Exception var3) {
            }


        }

        if (this.playerMouseInput != null) {
            this.playerMouseInput.setAccessible(true);
        }

        this.rightClickWaiting = false;
        autoClickerEnabled = false;
        clickFinder = 2;
        clickCount = 1;
    }

    @Override
    public void onEnable() {
        if (this.playerMouseInput == null) {
            this.onDisable();;
        }

        this.rightClickWaiting = false;
        this.allowedClick = false;
        //////System.out.println("Reset allowedClick");
        this.rand = new Random();
        autoClickerEnabled = true;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.leftDownTime = 0L;
        this.leftUpTime = 0L;
        boolean leftHeld = false;
        this.rightClickWaiting = false;
        autoClickerEnabled = false;
        super.onDisable();
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent ev) {
        if(!RavenUtils.currentScreenMinecraft())
            return;

        if(clickEvent.getValString() != "RENDER") {return;}

        if(clickTimings.getValString() == "Normal"){
            ravenClick();
        }
        else if (clickTimings.getValString() == "ClassLoader"){
            skidClick(ev, null);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent ev) {
        if(!RavenUtils.currentScreenMinecraft())
            return;

        if(clickEvent.getValString() != "TICK") {return;}

        if(clickTimings.getValString() == "Normal"){
            ravenClick();
        }
        else if (clickTimings.getValString() == "ClassLoader"){
            skidClick(null, ev);
        }
    }

    private void skidClick(TickEvent.RenderTickEvent er, TickEvent.PlayerTickEvent e) {
        if (!RavenUtils.isPlayerInGame())
            return;

        guiUpdate();

        double speedLeft1 = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getValDouble() - 0.2D, leftMaxCPS.getValDouble());
        double leftHoldLength = speedLeft1 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(leftMinCPS.getValDouble() - 0.02D, leftMaxCPS.getValDouble());
        double speedRight = 1.0 / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightMinCPS.getValDouble() - 0.2D, rightMaxCPS.getValDouble());
        double rightHoldLength = speedRight / io.netty.util.internal.ThreadLocalRandom.current().nextDouble(rightMinCPS.getValDouble() - 0.02D, rightMaxCPS.getValDouble());
        //If none of the buttons are allowed to click, what is the point in generating clicktimes anyway?
        //if (!leftActive && !rightActive) {
        // return;
        //}


        // Uhh left click only, mate
        if (Mouse.isButtonDown(0) && leftClick.isEnabled()) {
            if(breakBlock()) return;
            if (weaponOnly.isEnabled() && !RavenUtils.isPlayerHoldingWeapon()) {
                return;
            }
            if (jitterLeft.getValDouble() > 0.0D) {
                double a = jitterLeft.getValDouble() * 0.45D;
                EntityPlayerSP entityPlayer;
                if (this.rand.nextBoolean()) {
                    entityPlayer = mc.thePlayer;
                    entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * a);
                } else {
                    entityPlayer = mc.thePlayer;
                    entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * a);
                }

                if (this.rand.nextBoolean()) {
                    entityPlayer = mc.thePlayer;
                    entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * a * 0.45D);
                } else {
                    entityPlayer = mc.thePlayer;
                    entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * a * 0.45D);
                }
            }

            double speedLeft = 1.0 / ThreadLocalRandom.current().nextDouble(leftMinCPS.getValDouble() - 0.2, leftMaxCPS.getValDouble());
            if (System.currentTimeMillis() - lastClick > speedLeft * 1000) {
                lastClick = System.currentTimeMillis();
                if (leftHold < lastClick){
                    leftHold = lastClick;
                }
                int key = mc.gameSettings.keyBindAttack.getKeyCode();
                KeyBinding.setKeyBindState(key, true);

                KeyBinding.onTick(key);
                RavenUtils.setMouseButtonState(0, true);
            } else if (System.currentTimeMillis() - leftHold > leftHoldLength * 1000) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);

                RavenUtils.setMouseButtonState(0, false);
            }
        }
        //we cheat in a block game ft. right click
        if (Mouse.isButtonDown(1) && rightClick.isEnabled()) {
            if (!this.rightClickAllowed())
                return;


            if (jitterRight.getValDouble() > 0.0D) {
                double jitterMultiplier = jitterRight.getValDouble() * 0.45D;
                EntityPlayerSP entityPlayer;
                if (this.rand.nextBoolean()) {
                    entityPlayer = mc.thePlayer;
                    entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * jitterMultiplier);
                } else {
                    entityPlayer = mc.thePlayer;
                    entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * jitterMultiplier);
                }

                if (this.rand.nextBoolean()) {
                    entityPlayer = mc.thePlayer;
                    entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * jitterMultiplier * 0.45D);
                } else {
                    entityPlayer = mc.thePlayer;
                    entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * jitterMultiplier * 0.45D);
                }
            }

            if (System.currentTimeMillis() - lastClick > speedRight * 1000) {
                lastClick = System.currentTimeMillis();
                if (rightHold < lastClick){
                    rightHold = lastClick;
                }
                int key = mc.gameSettings.keyBindUseItem.getKeyCode();
                KeyBinding.setKeyBindState(key, true);
                //Utils.setMouseButtonState(1, true);
                if(clickCount/clickFinder == 0) {
                    //mouseManager.addRightClick();
                }
                clickCount++;
                KeyBinding.onTick(key);
            } else if (System.currentTimeMillis() - rightHold > rightHoldLength * 1000) {
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                //Utils.setMouseButtonState(1, false);

            }
        } else if (!Mouse.isButtonDown(1)){
            this.rightClickWaiting = false;
            this.allowedClick = false;
            //////System.out.println("Reset allowedClick");
        }
    }

    private void ravenClick() {
        if (mc.currentScreen == null && mc.inGameHasFocus) {

            //Mouse.poll();
            if (leftClick.isEnabled() && Mouse.isButtonDown(0)) {
                if (weaponOnly.isEnabled() && !RavenUtils.isPlayerHoldingWeapon()) {
                    return;
                }
                this.leftClickExecute(mc.gameSettings.keyBindAttack.getKeyCode());
            }
            else if (rightClick.isEnabled() && Mouse.isButtonDown(1)) {
                this.rightClickExecute(mc.gameSettings.keyBindUseItem.getKeyCode());
            } else if (!Mouse.isButtonDown(1)){
                this.rightClickWaiting = false;
                this.allowedClick = false;
                //////System.out.println("Reset allowedClick");
                this.righti = 0L;
                this.rightj = 0L;
                this.leftDownTime = 0L;
                this.leftUpTime = 0L;
            }
        } else if (inventoryFill.isEnabled() && mc.currentScreen instanceof GuiInventory) {
            if (!Mouse.isButtonDown(0) || !Keyboard.isKeyDown(54) && !Keyboard.isKeyDown(42)) {
                this.leftDownTime = 0L;
                this.leftUpTime = 0L;
            } else if (this.leftDownTime != 0L && this.leftUpTime != 0L) {
                if (System.currentTimeMillis() > this.leftUpTime) {
                    this.genLeftTimings();
                    this.inInvClick(mc.currentScreen);
                }
            } else {
                this.genLeftTimings();
            }
        }
    }

    public void guiUpdate() {
        RavenUtils.correctSliders(leftMinCPS, leftMaxCPS);
        RavenUtils.correctSliders(rightMinCPS, rightMaxCPS);
        RavenUtils.correctSliders(breakBlocksMin, breakBlocksMax);

    }

    public boolean rightClickAllowed() {
        ItemStack item = mc.thePlayer.getHeldItem();
        if (item != null) {
            if (allowEat.isEnabled()) {
                if ((item.getItem() instanceof ItemFood)) {
                    return false;
                }
            }
            if (allowBow.isEnabled()) {
                if (item.getItem() instanceof ItemBow) {
                    return false;
                }
            }
            if (onlyBlocks.isEnabled()) {
                if (!(item.getItem() instanceof ItemBlock))
                    return false;
            }
            if (noBlockSword.isEnabled()) {
                if (item.getItem() instanceof ItemSword)
                    return false;
            }
        }

        if(preferFastPlace.isEnabled()) {
            Module fastplace = Client.instance.moduleManager.getModule("FastPlace");
            if (fastplace.isToggled())
                return false;
        }

        if(rightClickDelay.getValDouble() != 0){
            if(!rightClickWaiting && !allowedClick) {
                this.rightClickWaitStartTime = System.currentTimeMillis();
                this.rightClickWaiting = true;
                //////System.out.println("Started waiting");
                return  false;
            } else if(this.rightClickWaiting && !allowedClick) {
                double passedTime = System.currentTimeMillis() - this.rightClickWaitStartTime;
                //////System.out.println("Waiting but not allowed");
                if (passedTime >= rightClickDelay.getValDouble()) {
                    this.allowedClick = true;
                    this.rightClickWaiting = false;
                    //////System.out.println("allowed");
                    return true;
                } else {
                    //////System.out.println("Waiting");
                    return false;
                }
            } else {
                //////System.out.println("Something else " + this.rightClickWaiting + " " + allowedClick);
            }
        }


        return true;
    }

    public void leftClickExecute(int key) {

        if(breakBlock()) return;

        if (jitterLeft.getValDouble() > 0.0D) {
            double a = jitterLeft.getValDouble() * 0.45D;
            EntityPlayerSP entityPlayer;
            if (this.rand.nextBoolean()) {
                entityPlayer = mc.thePlayer;
                entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * a);
            } else {
                entityPlayer = mc.thePlayer;
                entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * a);
            }

            if (this.rand.nextBoolean()) {
                entityPlayer = mc.thePlayer;
                entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * a * 0.45D);
            } else {
                entityPlayer = mc.thePlayer;
                entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * a * 0.45D);
            }
        }

        if (this.leftUpTime > 0L && this.leftDownTime > 0L) {
            if (System.currentTimeMillis() > this.leftUpTime) {
                KeyBinding.setKeyBindState(key, true);
                KeyBinding.onTick(key);
                this.genLeftTimings();
            } else if (System.currentTimeMillis() > this.leftDownTime) {
                KeyBinding.setKeyBindState(key, false);
            }
        } else {
            //////System.out.println("gen");
            this.genLeftTimings();
        }

    }

    public void rightClickExecute(int key) {
        if (!this.rightClickAllowed())
            return;

        if (jitterRight.getValDouble() > 0.0D) {
            double jitterMultiplier = jitterRight.getValDouble() * 0.45D;
            EntityPlayerSP entityPlayer;
            if (this.rand.nextBoolean()) {
                entityPlayer = mc.thePlayer;
                entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw + (double)this.rand.nextFloat() * jitterMultiplier);
            } else {
                entityPlayer = mc.thePlayer;
                entityPlayer.rotationYaw = (float)((double)entityPlayer.rotationYaw - (double)this.rand.nextFloat() * jitterMultiplier);
            }

            if (this.rand.nextBoolean()) {
                entityPlayer = mc.thePlayer;
                entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch + (double)this.rand.nextFloat() * jitterMultiplier * 0.45D);
            } else {
                entityPlayer = mc.thePlayer;
                entityPlayer.rotationPitch = (float)((double)entityPlayer.rotationPitch - (double)this.rand.nextFloat() * jitterMultiplier * 0.45D);
            }
        }

        if (this.rightj > 0L && this.righti > 0L) {
            if (System.currentTimeMillis() > this.rightj) {
                KeyBinding.setKeyBindState(key, true);
                KeyBinding.onTick(key);
                RavenUtils.setMouseButtonState(1, false);
                RavenUtils.setMouseButtonState(1, true);
                this.genRightTimings();
            } else if (System.currentTimeMillis() > this.righti) {
                KeyBinding.setKeyBindState(key, false);
                //Utils.setMouseButtonState(1, false);
            }
        } else {
            this.genRightTimings();
        }

    }

    public void genLeftTimings() {
        double clickSpeed = RavenUtils.ranModuleVal(leftMinCPS, leftMaxCPS, this.rand) + 0.4D * this.rand.nextDouble();
        long delay = (int)Math.round(1000.0D / clickSpeed);
        if (System.currentTimeMillis() > this.leftk) {
            if (!this.leftn && this.rand.nextInt(100) >= 85) {
                this.leftn = true;
                this.leftm = 1.1D + this.rand.nextDouble() * 0.15D;
            } else {
                this.leftn = false;
            }

            this.leftk = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
        }

        if (this.leftn) {
            delay = (long)((double)delay * this.leftm);
        }

        if (System.currentTimeMillis() > this.leftl) {
            if (this.rand.nextInt(100) >= 80) {
                delay += 50L + (long)this.rand.nextInt(100);
            }

            this.leftl = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
        }

        this.leftUpTime = System.currentTimeMillis() + delay;
        this.leftDownTime = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
    }

    public void genRightTimings() {
        double clickSpeed = RavenUtils.ranModuleVal(rightMinCPS, rightMaxCPS, this.rand) + 0.4D * this.rand.nextDouble();
        long delay = (int)Math.round(1000.0D / clickSpeed);
        if (System.currentTimeMillis() > this.rightk) {
            if (!this.rightn && this.rand.nextInt(100) >= 85) {
                this.rightn = true;
                this.rightm = 1.1D + this.rand.nextDouble() * 0.15D;
            } else {
                this.rightn = false;
            }

            this.rightk = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
        }

        if (this.rightn) {
            delay = (long)((double)delay * this.rightm);
        }

        if (System.currentTimeMillis() > this.rightl) {
            if (this.rand.nextInt(100) >= 80) {
                delay += 50L + (long)this.rand.nextInt(100);
            }

            this.rightl = System.currentTimeMillis() + 500L + (long)this.rand.nextInt(1500);
        }

        this.rightj = System.currentTimeMillis() + delay;
        this.righti = System.currentTimeMillis() + delay / 2L - (long)this.rand.nextInt(10);
    }

    private void inInvClick(GuiScreen guiScreen) {
        int mouseInGUIPosX = Mouse.getX() * guiScreen.width / mc.displayWidth;
        int mouseInGUIPosY = guiScreen.height - Mouse.getY() * guiScreen.height / mc.displayHeight - 1;

        try {
            this.playerMouseInput.invoke(guiScreen, mouseInGUIPosX, mouseInGUIPosY, 0);
        } catch (IllegalAccessException | InvocationTargetException var5) {
        }

    }

    public boolean breakBlock() {
        if (breakBlocks.isEnabled() && mc.objectMouseOver != null) {
            BlockPos p = mc.objectMouseOver.getBlockPos();
            if (p != null) {
                Block bl = mc.theWorld.getBlockState(p).getBlock();
                if (bl != Blocks.air && !(bl instanceof BlockLiquid)) {
                    if(breakBlocksMax.getValDouble() == 0){
                        if(!breakHeld) {
                            int e = mc.gameSettings.keyBindAttack.getKeyCode();
                            KeyBinding.setKeyBindState(e, true);
                            KeyBinding.onTick(e);
                            breakHeld = true;
                        }
                        return true;
                    }
                    if(!breakTimeDone && !watingForBreakTimeout) {
                        watingForBreakTimeout = true;
                        guiUpdate();
                        breakBlockFinishWaitTime = ThreadLocalRandom.current().nextDouble(breakBlocksMin.getValDouble(), breakBlocksMax.getValDouble()+1) + System.currentTimeMillis();
                        return false;
                    } else if(!breakTimeDone && watingForBreakTimeout) {
                        if (System.currentTimeMillis() > breakBlockFinishWaitTime) {
                            breakTimeDone = true;
                            watingForBreakTimeout = false;
                        }
                    }

                    if(breakTimeDone && !watingForBreakTimeout) {
                        if(!breakHeld) {
                            int e = mc.gameSettings.keyBindAttack.getKeyCode();
                            KeyBinding.setKeyBindState(e, true);
                            KeyBinding.onTick(e);
                            breakHeld = true;
                        }
                        return true;
                    }
                }
                if(breakHeld) {
                    breakHeld = false;
                    breakTimeDone = false;
                    watingForBreakTimeout = false;
                }
            }
        }
        return false;
    }
}
