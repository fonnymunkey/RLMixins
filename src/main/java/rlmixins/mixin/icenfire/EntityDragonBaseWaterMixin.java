package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;

@Mixin(EntityDragonBase.class)
public abstract class EntityDragonBaseWaterMixin extends EntityTameable {

    @Shadow(remap = false) public abstract boolean canMove();
    @Shadow(remap = false) private boolean isHovering;
    @Shadow(remap = false) private boolean isFlying;
    @Shadow(remap = false) public abstract void setHovering(boolean hovering);
    @Shadow(remap = false) public abstract void setSleeping(boolean sleeping);
    @Shadow(remap = false) protected int flyHovering;
    @Shadow(remap = false) private int hoverTicks;
    @Shadow(remap = false) public int flyTicks;

    @Shadow public abstract boolean isChild();

    public EntityDragonBaseWaterMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/ilexiconn/llibrary/server/entity/EntityPropertiesHandler;getProperties(Lnet/minecraft/entity/Entity;Ljava/lang/Class;)Lnet/ilexiconn/llibrary/server/entity/EntityProperties;", shift = At.Shift.BEFORE, remap = false)
    )
    public void rlmixins_iceAndFireEntityDragonBase_onLivingUpdate(CallbackInfo ci) {
        if(!this.world.isRemote
                && this.inWater
                && this.getAttackTarget() != null
                && this.getRNG().nextInt(15) == 0
                && this.canMove()
                && !this.isHovering
                && !this.isFlying
                && !this.isChild()) {
            this.setHovering(true);
            this.setSleeping(false);
            this.setSitting(false);
            this.flyHovering = 0;
            this.hoverTicks = 0;
            this.flyTicks = 0;
        }
    }
}
