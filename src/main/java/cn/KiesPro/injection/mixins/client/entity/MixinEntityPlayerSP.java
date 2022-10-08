package cn.KiesPro.injection.mixins.client.entity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cn.KiesPro.Client;
import cn.KiesPro.event.eventapi.EventManager;
import cn.KiesPro.event.events.EventChat;
import cn.KiesPro.event.events.EventMove;
import cn.KiesPro.event.events.EventPostUpdate;
import cn.KiesPro.event.events.EventPreUpdate;
import cn.KiesPro.event.events.EventStrafe;
import cn.KiesPro.event.events.EventUpdate;
import cn.KiesPro.injection.interfaces.IEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP extends AbstractClientPlayer implements IEntityPlayerSP {

    @Shadow
    public MovementInput movementInput;
    
    @Shadow
    private int horseJumpPowerCounter;
    @Shadow
    public int sprintingTicksLeft;
    @Shadow
    protected int sprintToggleTimer;
    @Shadow
    private float horseJumpPower;
    @Shadow
    public float timeInPortal;
    @Shadow
    public float prevTimeInPortal;
    
    @Final
    @Shadow
    public NetHandlerPlayClient sendQueue;
    @Shadow
    protected Minecraft mc;
    @Shadow
    private boolean serverSprintState;
    @Shadow
    private boolean serverSneakState;
    @Shadow
    private double lastReportedPosX;
    @Shadow
    private double lastReportedPosY;
    @Shadow
    private double lastReportedPosZ;
    @Shadow
    private float lastReportedYaw;
    @Shadow
    private float lastReportedPitch;
    @Shadow
    private int positionUpdateTicks;
    
    @Inject(method="onUpdate", at={@At(value="HEAD")})
    public void onUpdate(CallbackInfo callbackInfo) {
        EventManager.call(new EventUpdate());
    }
    
    public MixinEntityPlayerSP() {
        super(null, null);
    }
    
    /*
     * For onLivingUpdate
     */
    @Shadow
    protected void sendHorseJump() {
        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.RIDING_JUMP, (int)(this.getHorseJumpPower() * 100.0F)));
    }
    
    @Shadow
    public boolean isRidingHorse() {
        return this.ridingEntity != null && this.ridingEntity instanceof EntityHorse && ((EntityHorse)this.ridingEntity).isHorseSaddled();
    }
    
    @Shadow
    public float getHorseJumpPower() {
        return this.horseJumpPower;
    }
    
    @Overwrite
    public void onLivingUpdate()
    {
        if (this.sprintingTicksLeft > 0)
        {
            --this.sprintingTicksLeft;

            if (this.sprintingTicksLeft == 0)
            {
                this.setSprinting(false);
            }
        }

        if (this.sprintToggleTimer > 0)
        {
            --this.sprintToggleTimer;
        }

        this.prevTimeInPortal = this.timeInPortal;

        if (this.inPortal)
        {
            if (this.mc.currentScreen != null && !this.mc.currentScreen.doesGuiPauseGame())
            {
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (this.timeInPortal == 0.0F)
            {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
            }

            this.timeInPortal += 0.0125F;

            if (this.timeInPortal >= 1.0F)
            {
                this.timeInPortal = 1.0F;
            }

            this.inPortal = false;
        }
        else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60)
        {
            this.timeInPortal += 0.006666667F;

            if (this.timeInPortal > 1.0F)
            {
                this.timeInPortal = 1.0F;
            }
        }
        else
        {
            if (this.timeInPortal > 0.0F)
            {
                this.timeInPortal -= 0.05F;
            }

            if (this.timeInPortal < 0.0F)
            {
                this.timeInPortal = 0.0F;
            }
        }

        if (this.timeUntilPortal > 0)
        {
            --this.timeUntilPortal;
        }

        boolean flag = this.movementInput.jump;
        boolean flag1 = this.movementInput.sneak;
        float f = 0.8F;
        boolean flag2 = this.movementInput.moveForward >= f;
        this.movementInput.updatePlayerMoveState();

        if (this.isUsingItem() && !this.isRiding() && !Client.instance.moduleManager.getModule("NoSlow").isToggled()) {
            this.movementInput.moveStrafe *= 0.2F;
            this.movementInput.moveForward *= 0.2F;
            this.sprintToggleTimer = 0;
        }

        this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX - (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ - (double)this.width * 0.35D);
        this.pushOutOfBlocks(this.posX + (double)this.width * 0.35D, this.getEntityBoundingBox().minY + 0.5D, this.posZ + (double)this.width * 0.35D);
        boolean flag3 = (float)this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;

        if (this.onGround && !flag1 && !flag2 && this.movementInput.moveForward >= f && !this.isSprinting() && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness))
        {
            if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.isKeyDown())
            {
                this.sprintToggleTimer = 7;
            }
            else
            {
                this.setSprinting(true);
            }
        }

        if (!this.isSprinting() && this.movementInput.moveForward >= f && flag3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.isKeyDown())
        {
            this.setSprinting(true);
        }

        if (this.isSprinting() && (this.movementInput.moveForward < f || this.isCollidedHorizontally || !flag3))
        {
            this.setSprinting(false);
        }

        if (this.capabilities.allowFlying)
        {
            if (this.mc.playerController.isSpectatorMode())
            {
                if (!this.capabilities.isFlying)
                {
                    this.capabilities.isFlying = true;
                    this.sendPlayerAbilities();
                }
            }
            else if (!flag && this.movementInput.jump)
            {
                if (this.flyToggleTimer == 0)
                {
                    this.flyToggleTimer = 7;
                }
                else
                {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }
        }

        if (this.capabilities.isFlying && this.isCurrentViewEntity())
        {
            if (this.movementInput.sneak)
            {
                this.motionY -= (double)(this.capabilities.getFlySpeed() * 3.0F);
            }

            if (this.movementInput.jump)
            {
                this.motionY += (double)(this.capabilities.getFlySpeed() * 3.0F);
            }
        }

        if (this.isRidingHorse())
        {
            if (this.horseJumpPowerCounter < 0)
            {
                ++this.horseJumpPowerCounter;

                if (this.horseJumpPowerCounter == 0)
                {
                    this.horseJumpPower = 0.0F;
                }
            }

            if (flag && !this.movementInput.jump)
            {
                this.horseJumpPowerCounter = -10;
                this.sendHorseJump();
            }
            else if (!flag && this.movementInput.jump)
            {
                this.horseJumpPowerCounter = 0;
                this.horseJumpPower = 0.0F;
            }
            else if (flag)
            {
                ++this.horseJumpPowerCounter;

                if (this.horseJumpPowerCounter < 10)
                {
                    this.horseJumpPower = (float)this.horseJumpPowerCounter * 0.1F;
                }
                else
                {
                    this.horseJumpPower = 0.8F + 2.0F / (float)(this.horseJumpPowerCounter - 9) * 0.1F;
                }
            }
        }
        else
        {
            this.horseJumpPower = 0.0F;
        }

        super.onLivingUpdate();

        if (this.onGround && this.capabilities.isFlying && !this.mc.playerController.isSpectatorMode())
        {
            this.capabilities.isFlying = false;
            this.sendPlayerAbilities();
        }
    }
    @Overwrite
    public void onUpdateWalkingPlayer() {
        EventPreUpdate e = new EventPreUpdate(this.rotationYaw, this.rotationPitch, this.posY, Minecraft.getMinecraft().thePlayer.onGround);
        EventPostUpdate post = new EventPostUpdate(this.rotationYaw, this.rotationPitch);
        EventManager.call(e);
        if (e.isCancelled()) {
        	EventManager.call(post);
            return;
        }
        double oldX = this.posX;
        double oldZ = this.posZ;
        float oldPitch = this.rotationPitch;
        float oldYaw = this.rotationYaw;
        boolean oldGround = this.onGround;
        this.rotationPitch = e.getPitch();
        this.rotationYaw = e.getYaw();
        this.onGround = e.isOnground();
        
        boolean flag = this.isSprinting();

        if (flag != this.serverSprintState)
        {
            if (flag)
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SPRINTING));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SPRINTING));
            }

            this.serverSprintState = flag;
        }

        boolean flag1 = this.isSneaking();

        if (flag1 != this.serverSneakState)
        {
            if (flag1)
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.START_SNEAKING));
            }
            else
            {
                this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }

            this.serverSneakState = flag1;
        }

        if (this.isCurrentViewEntity())
        {
            double d0 = this.posX - this.lastReportedPosX;
            double d1 = this.getEntityBoundingBox().minY - this.lastReportedPosY;
            double d2 = this.posZ - this.lastReportedPosZ;
            double d3 = (double)(this.rotationYaw - this.lastReportedYaw);
            double d4 = (double)(this.rotationPitch - this.lastReportedPitch);
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.positionUpdateTicks >= 20;
            boolean flag3 = d3 != 0.0D || d4 != 0.0D;

            if (this.ridingEntity == null)
            {
                if (flag2 && flag3)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.posX, e.getY(), this.posZ, this.rotationYaw, this.rotationPitch, e.isOnground()));
                }
                else if (flag2)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.posX, e.getY(), this.posZ, e.isOnground()));
                }
                else if (flag3)
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.rotationYaw, this.rotationPitch, e.isOnground()));
                }
                else
                {
                    this.sendQueue.addToSendQueue(new C03PacketPlayer(e.isOnground()));
                }
            }
            else
            {
                this.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.motionX, -999.0D, this.motionZ, this.rotationYaw, this.rotationPitch, this.onGround));
                flag2 = false;
            }

            ++this.positionUpdateTicks;

            if (flag2)
            {
                this.lastReportedPosX = this.posX;
                this.lastReportedPosY = this.getEntityBoundingBox().minY;
                this.lastReportedPosZ = this.posZ;
                this.positionUpdateTicks = 0;
            }

            if (flag3)
            {
                this.lastReportedYaw = this.rotationYaw;
                this.lastReportedPitch = this.rotationPitch;
            }
        }
        this.posX = oldX;
        this.posZ = oldZ;
        this.rotationYaw = oldYaw;
        this.rotationPitch = oldPitch;
        this.onGround = oldGround;
        EventManager.call(post);
    }
    
    @Shadow
    public boolean isCurrentViewEntity() {
        return false;
    }

	@Overwrite
    public void sendChatMessage(String message)
    {
		EventChat event = new EventChat(message);
		EventManager.call(event);
		
		if (event.isCancelled()) {
			return;
		}
		
        mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(message));
    }
	
	@Override
    public void moveFlying(float strafe, float forward, float friction) {
        float yaw = this.rotationYaw;

        EventStrafe event = new EventStrafe(forward, strafe, friction, yaw);
        EventManager.call(event);
        if (event.isCancelled()) return;
        strafe = event.strafe;
        forward = event.forward;
        friction = event.friction;
        yaw = event.yaw;

        float f = strafe * strafe + forward * forward;

        if (f >= 1.0E-4F) {
            f = MathHelper.sqrt_float(f);

            if (f < 1.0F) {
                f = 1.0F;
            }

            f = friction / f;
            strafe = strafe * f;
            forward = forward * f;
            float f1 = MathHelper.sin(yaw * (float) Math.PI / 180.0F);
            float f2 = MathHelper.cos(yaw * (float) Math.PI / 180.0F);
            this.motionX += strafe * f2 - forward * f1;
            this.motionZ += forward * f2 + strafe * f1;
        }

    }

	public void moveEntity(double x, double y, double z) {
        EventMove event = new EventMove(x, y, z);
        EventManager.call(event);
        super.moveEntity(event.getX(), event.getY(), event.getZ());
    }
	

	
//    public void onMoveEntity(double x, double y, double z) {
//
//
//            final List<AxisAlignedBB> list1 = (List<AxisAlignedBB>)this.worldObj.getCollidingBoundingBoxes((Entity)this, this.getEntityBoundingBox().addCoord(x, y, z));
//            final AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
//            for (final AxisAlignedBB axisalignedbb2 : list1) {
//                y = axisalignedbb2.calculateYOffset(this.getEntityBoundingBox(), y);
//            }
//            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, y, 0.0));
//            final boolean flag2 = this.onGround || (d5 != y && d5 < 0.0);
//            for (final AxisAlignedBB axisalignedbb3 : list1) {
//                x = axisalignedbb3.calculateXOffset(this.getEntityBoundingBox(), x);
//            }
//            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, 0.0, 0.0));
//            for (final AxisAlignedBB axisalignedbb4 : list1) {
//                z = axisalignedbb4.calculateZOffset(this.getEntityBoundingBox(), z);
//            }
//            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, z));
//
//            if (this.stepHeight > 0.0f && flag2 && (d4 != x || d5 != z)) {
//                EventStep es = new EventStep(EventType.PRE, this.stepHeight);
//                EventManager.call(es);
//                
//                
//                
//                
//                
//                double d11 = x;
//                double d7 = y;
//                double d8 = z;
//                AxisAlignedBB axisalignedbb3 = this.getEntityBoundingBox();
//                this.setEntityBoundingBox(axisalignedbb);
//                y = stepHeight;
//                List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this,
//                        this.getEntityBoundingBox().addCoord(d3, y, d5));
//                AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
//                AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(d3, 0.0D, d5);
//                double d9 = y;
//
//                for (AxisAlignedBB axisalignedbb6 : list) {
//                    d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9);
//                }
//
//                axisalignedbb4 = axisalignedbb4.offset(0.0D, d9, 0.0D);
//                double d15 = d3;
//
//                for (AxisAlignedBB axisalignedbb7 : list) {
//                    d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15);
//                }
//
//                axisalignedbb4 = axisalignedbb4.offset(d15, 0.0D, 0.0D);
//                double d16 = d5;
//
//                for (AxisAlignedBB axisalignedbb8 : list) {
//                    d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16);
//                }
//
//                axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d16);
//                AxisAlignedBB axisalignedbb14 = this.getEntityBoundingBox();
//                double d17 = y;
//
//                for (AxisAlignedBB axisalignedbb9 : list) {
//                    d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17);
//                }
//
//                axisalignedbb14 = axisalignedbb14.offset(0.0D, d17, 0.0D);
//                double d18 = d3;
//
//                for (AxisAlignedBB axisalignedbb10 : list) {
//                    d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18);
//                }
//
//                axisalignedbb14 = axisalignedbb14.offset(d18, 0.0D, 0.0D);
//                double d19 = d5;
//
//                for (AxisAlignedBB axisalignedbb11 : list) {
//                    d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19);
//                }
//
//                axisalignedbb14 = axisalignedbb14.offset(0.0D, 0.0D, d19);
//                double d20 = d15 * d15 + d16 * d16;
//                double d10 = d18 * d18 + d19 * d19;
//
//                if (d20 > d10) {
//                    x = d15;
//                    z = d16;
//                    y = -d9;
//                    this.setEntityBoundingBox(axisalignedbb4);
//                } else {
//                    x = d18;
//                    z = d19;
//                    y = -d17;
//                    this.setEntityBoundingBox(axisalignedbb14);
//                }
//
//                for (AxisAlignedBB axisalignedbb12 : list) {
//                    y = axisalignedbb12.calculateYOffset(this.getEntityBoundingBox(), y);
//                }
//
//                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));
//
//                if (d11 * d11 + d8 * d8 >= x * x + z * z) {
//                    x = d11;
//                    y = d7;
//                    z = d8;
//                    this.setEntityBoundingBox(axisalignedbb3);
//                } else {
//                    EventStep post = new EventStep(EventType.POST, stepHeight);
//                    EventManager.call(post);
//                }
//            }
//
//            this.worldObj.theProfiler.endSection();
//            this.worldObj.theProfiler.startSection("rest");
//            this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0;
//            this.posY = this.getEntityBoundingBox().minY;
//            this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0;
//            this.isCollidedHorizontally = (d4 != x || d5 != z);
//            this.isCollidedVertically = (d5 != y);
//            this.onGround = (this.isCollidedVertically && d5 < 0.0);
//            this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
//            final int i = MathHelper.floor_double(this.posX);
//            final int j = MathHelper.floor_double(this.posY - 0.20000000298023224);
//            final int k = MathHelper.floor_double(this.posZ);
//            BlockPos blockpos = new BlockPos(i, j, k);
//            Block block1 = this.worldObj.getBlockState(blockpos).getBlock();
//            if (block1.getMaterial() == Material.air) {
//                final Block block2 = this.worldObj.getBlockState(blockpos.down()).getBlock();
//                if (block2 instanceof BlockFence || block2 instanceof BlockWall || block2 instanceof BlockFenceGate) {
//                    block1 = block2;
//                    blockpos = blockpos.down();
//                }
//            }
//            this.updateFallState(y, this.onGround, block1, blockpos);
//            if (d3 != x) {
//                this.motionX = 0.0;
//            }
//            if (d5 != z) {
//                this.motionZ = 0.0;
//            }
//            if (d4 != y) {
//                block1.onLanded(this.worldObj, (Entity)this);
//            }
//            final IEntity ent = (IEntity)this;
//            if (this.canTriggerWalking() && !flag && this.ridingEntity == null) {
//                final double d19 = this.posX - d0;
//                double d20 = this.posY - d2;
//                final double d21 = this.posZ - d3;
//                if (block1 != Blocks.ladder) {
//                    d20 = 0.0;
//                }
//                if (block1 != null && this.onGround) {
//                    block1.onEntityCollidedWithBlock(this.worldObj, blockpos, (Entity)this);
//                }
//                this.distanceWalkedModified += (float)(MathHelper.sqrt_double(d19 * d19 + d21 * d21) * 0.6);
//                this.distanceWalkedOnStepModified += (float)(MathHelper.sqrt_double(d19 * d19 + d20 * d20 + d21 * d21) * 0.6);
//                if (this.distanceWalkedOnStepModified > ent.getNextStepDistance() && block1.getMaterial() != Material.air) {
//                    ent.setNextStepDistance((int)this.distanceWalkedOnStepModified + 1);
//                    if (this.isInWater()) {
//                        float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224 + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224) * 0.35f;
//                        if (f > 1.0f) {
//                            f = 1.0f;
//                        }
//                        this.playSound(this.getSwimSound(), f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
//                    }
//                    this.playStepSound(blockpos, block1);
//                }
//            }
//            try {
//                this.doBlockCollisions();
//            }
//            catch (Throwable throwable) {
//                final CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
//                final CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
//                this.addEntityCrashInfo(crashreportcategory);
//                throw new ReportedException(crashreport);
//            }
//
//            boolean flag3 = this.isWet();
//
//            if (this.worldObj.isFlammableWithin(this.getEntityBoundingBox().contract(0.001D, 0.001D, 0.001D))) {
//                this.dealFireDamage(1);
//
//                if (!flag3) {
//                    ent.setFire(ent.getFire() + 1);
//
//                    if (ent.getFire() == 0) {
//                        this.setFire(8);
//                    }
//                }
//            } else if (ent.getFire() <= 0) {
//                ent.setFire(-this.fireResistance);
//            }
//
//            if (flag3 && ent.getFire() > 0) {
//                this.playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
//                ent.setFire(-this.fireResistance);
//            }
//
//            this.worldObj.theProfiler.endSection();
//        }
//    }

    public boolean moving() {
        return this.moveForward > 0.0 | this.moveStrafing > 0.0;
    }

    public float getSpeed() {
        return (float) Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
    }

    public void setSpeed(final double speed) {
        this.motionX = -MathHelper.sin(this.getDirection()) * speed;
        this.motionZ = MathHelper.cos(this.getDirection()) * speed;
    }

    public float getDirection() {
        float yaw = this.rotationYaw;
        final float forward = this.moveForward;
        final float strafe = this.moveStrafing;
        yaw += ((forward < 0.0f) ? 180 : 0);

        if (strafe < 0.0f) {
            yaw += ((forward < 0.0f) ? -45.0f : ((forward == 0.0f) ? 90.0f : 45.0f));
        }

        if (strafe > 0.0f) {
            yaw -= ((forward < 0.0f) ? -45.0f : ((forward == 0.0f) ? 90.0f : 45.0f));
        }

        return yaw * 0.017453292f;
    }


    public void setYaw(double yaw) {
        this.rotationYaw = (float) yaw;
    }

    public void setPitch(double pitch) {
        this.rotationPitch = (float) pitch;
    }

    public void setMoveSpeed(EventMove event, double speed) {
        double forward = this.movementInput.moveForward;
        double strafe = this.movementInput.moveStrafe;
        float yaw = this.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f))  - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }

    @Override
    public void setLastReportedPosY(double f) {
        lastReportedPosY = f;
    }
}
