package cn.KiesPro.module.combat;

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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

public class Reach extends Module {
	
    public static Setting throughBlocks;
    public static Setting reachMaxVal;
    public static  Setting reachMinVal;
    public  Random r;
    public static  Field pZ;
    public static  Field pX;

    public Reach() {
        super("Reach", "Reach", Category.COMBAT);
        r = new Random();
        reachMinVal = new Setting("Min Reach",this, 3.0, 3.0, 6.0, true);
         this.registerSetting(reachMinVal);
        reachMaxVal = new Setting("Max Reach",this,  3.0, 3.0, 6.0, true);
         this.registerSetting(reachMaxVal);
        throughBlocks = new Setting("Through Blocks", this, false);
         this.registerSetting(throughBlocks);
    }

    public static Object[] getMouseOver(double Range, double bbExpand, float f) {
        Entity renderViewEntity = mc.getRenderViewEntity();
        Entity entity = null;
        if (renderViewEntity == null || mc.theWorld == null) {
            return null;
        }
        mc.mcProfiler.startSection("pick");
        Vec3 positionEyes = renderViewEntity.getPositionEyes(0.0f);
        Vec3 renderViewEntityLook = renderViewEntity.getLook(0.0f);
        Vec3 vector = positionEyes.addVector(renderViewEntityLook.xCoord * Range, renderViewEntityLook.yCoord * Range, renderViewEntityLook.zCoord * Range);
        Vec3 ve = null;
        List entitiesWithinAABB = mc.theWorld.getEntitiesWithinAABBExcludingEntity(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(renderViewEntityLook.xCoord * Range, renderViewEntityLook.yCoord * Range, renderViewEntityLook.zCoord * Range).expand(1.0, 1.0, 1.0));
        double range = Range;
        for (int i = 0; i < entitiesWithinAABB.size(); ++i) {
            double v;
            Entity e = (Entity)entitiesWithinAABB.get(i);
            if (!e.canBeCollidedWith()) continue;
            float size = e.getCollisionBorderSize();
            AxisAlignedBB bb = e.getEntityBoundingBox().expand((double)size, (double)size, (double)size);
            bb = bb.expand(bbExpand, bbExpand, bbExpand);
            MovingObjectPosition objectPosition = bb.calculateIntercept(positionEyes, vector);
            if (bb.isVecInside(positionEyes)) {
                if (!(0.0 < range) && range != 0.0) continue;
                entity = e;
                ve = objectPosition == null ? positionEyes : objectPosition.hitVec;
                range = 0.0;
                continue;
            }
            if (objectPosition == null || !((v = positionEyes.distanceTo(objectPosition.hitVec)) < range) && range != 0.0) continue;
            boolean b = false;
            if (e == renderViewEntity.ridingEntity) {
                if (range != 0.0) continue;
                entity = e;
                ve = objectPosition.hitVec;
                continue;
            }
            entity = e;
            ve = objectPosition.hitVec;
            range = v;
        }
        if (range < Range && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
        }
        mc.mcProfiler.endSection();
        if (entity == null || ve == null) {
            return null;
        }
        return new Object[]{entity, ve};
    }

    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        if(!Utils.currentScreenMinecraft())
            return;

        if (!throughBlocks.isEnabled() && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            return;
        }
        double range = reachMinVal.getValDouble() + r.nextDouble() * (reachMaxVal.getValDouble() - reachMinVal.getValDouble());
        Object[] mouseOver = getMouseOver(range, 0.0, 0.0f);
        if (mouseOver == null) {
            return;
        }
        mc.objectMouseOver = new MovingObjectPosition((Entity)mouseOver[0], (Vec3)mouseOver[1]);
        mc.pointedEntity = (Entity)mouseOver[0];
    }
}