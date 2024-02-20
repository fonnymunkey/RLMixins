package rlmixins.mixin.mobends;

import goblinbob.mobends.standard.client.renderer.entity.ArrowTrail;
import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.mixin.vanilla.EntityArrowAccessor;

@Mixin(ArrowTrail.class)
public abstract class ArrowTrailMixin {

    @Shadow(remap = false) private EntityArrow trackedArrow;

    @Inject(
            method = "shouldBeRemoved",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_moBendsArrowTrail_shouldBeRemoved(CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) cir.setReturnValue(((EntityArrowAccessor)this.trackedArrow).getInGround());
    }
}