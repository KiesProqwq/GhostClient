package cn.KiesPro.module.combat;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.raven.RavenUtils;

import java.util.concurrent.ThreadLocalRandom;

public class WTap extends Module {

    public static Setting range;
    public static Setting onlyPlayers;
    public static Setting minActionTicks, maxActionTicks, minOnceEvery, maxOnceEvery;
    public static double comboLasts;
    public static boolean comboing, hitCoolDown, alreadyHit;
    public static int hitTimeout, hitsWaited;
    
    public WTap() {
		super("WTap", "Auto WTap(自动控距)", Category.COMBAT);

        this.registerSetting(onlyPlayers = new Setting("Players Only",this ,true));
        this.registerSetting(minActionTicks = new Setting("Min Delay: ",this , 5, 1, 100, true));
        this.registerSetting(maxActionTicks = new Setting("Man Delay: ", this ,12, 1, 100, true));
        this.registerSetting(minOnceEvery = new Setting("Min Hits: ",this , 1, 1, 10, true));
        this.registerSetting(maxOnceEvery = new Setting("Max Hits: ", this ,1, 1, 10, true));
        this.registerSetting(range = new Setting("Range: ", this ,3, 1, 6, false));

    }

    public void guiUpdate() {
        RavenUtils.correctSliders(minActionTicks, maxActionTicks);
        RavenUtils.correctSliders(minOnceEvery, maxOnceEvery);
    }


    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent e) {
        if(!RavenUtils.isPlayerInGame())
            return;

        if(comboing) {
            if(System.currentTimeMillis() >= comboLasts){
                comboing = false;
                finishCombo();
                return;
            }else {
                return;
            }
        }



        if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit instanceof Entity && Mouse.isButtonDown(0)) {
            Entity target = mc.objectMouseOver.entityHit;
            //////////System.out.println(target.hurtResistantTime);
            if(target.isDead) {
                return;
            }

            if (mc.thePlayer.getDistanceToEntity(target) <= range.getValDouble()) {
                if (target.hurtResistantTime >= 10) {

                    if (onlyPlayers.isEnabled()){
                        if (!(target instanceof EntityPlayer)){
                            return;
                        }
                    }
                    //if(AntiBot.bots.(target)){
                    //    return;
                    //}


                    if (hitCoolDown && !alreadyHit) {
                        //////////System.out.println("coolDownCheck");
                        hitsWaited++;
                        if(hitsWaited >= hitTimeout){
                            //////////System.out.println("hiit cool down reached");
                            hitCoolDown = false;
                            hitsWaited = 0;
                        } else {
                            //////////System.out.println("still waiting for cooldown");
                            alreadyHit = true;
                            return;
                        }
                    }

                    //////////System.out.println("Continued");

                    if(!alreadyHit){
                        //////////System.out.println("Startring combo code");
                        guiUpdate();
                        if(minOnceEvery.getValDouble() == maxOnceEvery.getValDouble()) {
                            hitTimeout =  (int)minOnceEvery.getValDouble();
                        } else {

                            hitTimeout = ThreadLocalRandom.current().nextInt((int)minOnceEvery.getValDouble(), (int)maxOnceEvery.getValDouble());
                        }
                        hitCoolDown = true;
                        hitsWaited = 0;

                        comboLasts = ThreadLocalRandom.current().nextDouble(minActionTicks.getValDouble(),  maxActionTicks.getValDouble()+0.01) + System.currentTimeMillis();
                        comboing = true;
                        startCombo();
                        //////////System.out.println("Combo started");
                        alreadyHit = true;
                    }
                } else {
                    if(alreadyHit){
                        //////////System.out.println("UnHit");
                    }
                    alreadyHit = false;
                    //////////System.out.println("REEEEEEE");
                }
            }
        }
    }

    private static void finishCombo() {
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())){
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
    }

    private static void startCombo() {
        if(Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            KeyBinding.onTick(mc.gameSettings.keyBindForward.getKeyCode());
        }
    }

}
