package cn.KiesPro.module.combat;

import cn.KiesPro.Client;
import cn.KiesPro.module.Category;
import cn.KiesPro.module.Module;
import cn.KiesPro.settings.Setting;
import cn.KiesPro.utils.raven.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;
import java.util.List;

public class HitBox extends Module {
	
    public static Setting a;
    private static MovingObjectPosition mv;
    
	public HitBox() {
		super("HitBox", "HitBox", Category.COMBAT);
        this.registerSetting(a = new Setting("Multiplier",this, 1.20D, 1.00D, 50.00D, false));
	}
	
	@SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent ev) {
        if(!Utils.isPlayerInGame()) return;

        gmo(1.0F);
    }

    @SubscribeEvent
    public void onMouse(MouseEvent e) {
        if(!Utils.isPlayerInGame()) return;
        if (e.button == 0 && e.buttonstate && mv != null) {
            mc.objectMouseOver = mv;
        }
    }

    @SubscribeEvent
    public void ef(TickEvent.RenderTickEvent ev) {
        // autoclick event
        if(!Utils.isPlayerInGame()) return;
        Module autoclicker = Client.instance.moduleManager.getModule("AutoClicker");
        if(!autoclicker.isToggled()) return;

        if (autoclicker.isToggled() && Mouse.isButtonDown(0)){
            if (mv != null) {
                mc.objectMouseOver = mv;
            }
        }
    }

    public static double exp(Entity en) {
        return Client.instance.moduleManager.getModule("HitBox").isToggled() ? a.getValDouble() : 1.0D;
    }

    public static void gmo(float partialTicks) {
        if (mc.getRenderViewEntity() != null && mc.theWorld != null) {
            mc.pointedEntity = null;
            Entity pE = null;
            double d0 = 3.0D;
            mv = mc.getRenderViewEntity().rayTrace(d0, partialTicks);
            double d2 = d0;
            Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(partialTicks);
            if (mv != null) {
                d2 = mv.hitVec.distanceTo(vec3);
            }

            Vec3 vec4 = mc.getRenderViewEntity().getLook(partialTicks);
            Vec3 vec5 = vec3.addVector(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0);
            Vec3 vec6 = null;
            float f1 = 1.0F;
            List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec4.xCoord * d0, vec4.yCoord * d0, vec4.zCoord * d0).expand(f1, f1, f1));
            double d3 = d2;

            for (Object o : list) {
                Entity entity = (Entity) o;
                if (entity.canBeCollidedWith()) {
                    float ex = (float) ((double) entity.getCollisionBorderSize() * exp(entity));
                    AxisAlignedBB ax = entity.getEntityBoundingBox().expand(ex, ex, ex);
                    MovingObjectPosition mop = ax.calculateIntercept(vec3, vec5);
                    if (ax.isVecInside(vec3)) {
                        if (0.0D < d3 || d3 == 0.0D) {
                            pE = entity;
                            vec6 = mop == null ? vec3 : mop.hitVec;
                            d3 = 0.0D;
                        }
                    } else if (mop != null) {
                        double d4 = vec3.distanceTo(mop.hitVec);
                        if (d4 < d3 || d3 == 0.0D) {
                            if (entity == mc.getRenderViewEntity().ridingEntity && !entity.canRiderInteract()) {
                                if (d3 == 0.0D) {
                                    pE = entity;
                                    vec6 = mop.hitVec;
                                }
                            } else {
                                pE = entity;
                                vec6 = mop.hitVec;
                                d3 = d4;
                            }
                        }
                    }
                }
            }

            if (pE != null && (d3 < d2 || mv == null)) {
                mv = new MovingObjectPosition(pE, vec6);
                if (pE instanceof EntityLivingBase || pE instanceof EntityItemFrame) {
                    mc.pointedEntity = pE;
                }
            }
        }
    }
}
