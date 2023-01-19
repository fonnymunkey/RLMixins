package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.RLMixins;

@Mixin(EntityDragonBase.class)
public abstract class EntityDragonBaseCheeseMixin extends EntityTameable {

    private int worldTickCache = 0;

    public EntityDragonBaseCheeseMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "onUpdate",
            at = @At("RETURN")
    )
    public void rlmixins_iceAndFireEntityDragonBase_onUpdate(CallbackInfo ci) {
        this.worldTickCache = (int)this.world.getTotalWorldTime();
    }

    @Inject(
            method = "attackEntityFrom",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_iceAndFireEntityDragonBase_attackEntityFrom(DamageSource dmg, float i, CallbackInfoReturnable<Boolean> cir) {
        if(!this.world.isRemote && this.worldTickCache > 0 && this.worldTickCache < (int)this.world.getTotalWorldTime() - 3) {
            RLMixins.LOGGER.log(Level.WARN, "RLMixins detected attempt to damage Dragon while not ticking, cancelling");
            cir.setReturnValue(false);
        }
    }
}