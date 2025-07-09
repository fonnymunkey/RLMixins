package rlmixins.mixin.infrlcraft;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityDragonBase.class)
public abstract class EntityDragonBase_TickMixin extends EntityTameable {

    @Unique
    private int rlmixins$ticksExistedCache = 0;

    public EntityDragonBase_TickMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "onUpdate",
            at = @At("RETURN")
    )
    private void rlmixins_infRLCraftEntityDragonBase_onUpdate(CallbackInfo ci) {
        this.rlmixins$ticksExistedCache = this.ticksExisted;
    }

    @Inject(
            method = "attackEntityFrom",
            at = @At("HEAD"),
            cancellable = true
    )
    private void rlmixins_infRLCraftEntityDragonBase_attackEntityFrom(DamageSource dmg, float i, CallbackInfoReturnable<Boolean> cir) {
        if(this.rlmixins$ticksExistedCache > 0 && this.rlmixins$ticksExistedCache < this.ticksExisted - 5) {
            cir.setReturnValue(false);
        }
    }
}