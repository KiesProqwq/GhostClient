package cn.KiesPro.injection.mixins.client.renderer;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventManager;
import cn.KiesPro.event.events.EventRender3D;
import cn.KiesPro.module.Module;
import cn.KiesPro.module.combat.Reach;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.*;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.Objects;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.google.common.base.Predicates;


@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Shadow
    private Entity pointedEntity;

    @Shadow
    private Minecraft mc;

    @Shadow
    private boolean cloudFog;

    @Shadow
    public abstract void setupCameraTransform(float partialTicks, int pass);

    @Shadow
    private float thirdPersonDistanceTemp;

    @Shadow
    private float thirdPersonDistance;


    @Inject(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Vec3;distanceTo(Lnet/minecraft/util/Vec3;)D"), cancellable = true)
    public void orientCamera(float partialTicks, CallbackInfo ci) {
        if(Client.instance.moduleManager.getModule("ViewClip").isToggled()) {
            ci.cancel();
            Entity entity = this.mc.getRenderViewEntity();
            float f = entity.getEyeHeight();
            float f1;
            if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPlayerSleeping()) {
                f = (float) ((double) f + 1.0D);
                GlStateManager.translate(0.0F, 0.3F, 0.0F);
                if (!this.mc.gameSettings.debugCamEnable) {
                    BlockPos blockpos = new BlockPos(entity);
                    IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
                    ForgeHooksClient.orientBedCamera(this.mc.theWorld, blockpos, iblockstate, entity);
                    GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
                    GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
                }
            } else if (this.mc.gameSettings.thirdPersonView > 0) {
                double d3 = (double) (this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks);
                if (this.mc.gameSettings.debugCamEnable) {
                    GlStateManager.translate(0.0F, 0.0F, (float) (-d3));
                } else {
                    f1 = entity.rotationYaw;
                    float f2 = entity.rotationPitch;
                    if (this.mc.gameSettings.thirdPersonView == 2) {
                        f2 += 180.0F;
                    }

                    if (this.mc.gameSettings.thirdPersonView == 2) {
                        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    }

                    GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
                    GlStateManager.translate(0.0F, 0.0F, (float) (-d3));
                    GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
                }
            } else {
                GlStateManager.translate(0.0F, 0.0F, -0.1F);
            }

            if (!this.mc.gameSettings.debugCamEnable) {
                float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F;
                float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
                f1 = 0.0F;
                if (entity instanceof EntityAnimal) {
                    EntityAnimal entityanimal = (EntityAnimal) entity;
                    yaw = entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F;
                }

                Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, entity, partialTicks);
                EntityViewRenderEvent.CameraSetup event = new EntityViewRenderEvent.CameraSetup((EntityRenderer) (Object) this, entity, block, (double) partialTicks, yaw, pitch, f1);
                MinecraftForge.EVENT_BUS.post(event);
                GlStateManager.rotate(event.roll, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(event.pitch, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(event.yaw, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.translate(0.0F, -f, 0.0F);
            double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
            double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) f;
            double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;
            this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
        }
    }

    @Inject(method = "hurtCameraEffect", at = @At("HEAD"), cancellable = true)
    private void injectHurtCameraEffect(CallbackInfo callbackInfo) {
        if (Client.instance.moduleManager.getModule("NoHurtCam").isToggled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = "renderWorldPass", at =@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableFog()V", shift = Shift.AFTER))
    private void eventRender3D(int pass, float partialTicks, long finishTimeNano, CallbackInfo callbackInfo) {
    	EventRender3D eventRender3D = new EventRender3D(partialTicks);
        EventManager.call(eventRender3D);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
}
