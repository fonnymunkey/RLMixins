package rlmixins.mixin.vanilla;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.wrapper.BetterSurvivalWrapper;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin {

    @Inject(
            method = "attemptTeleport",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_vanillaEntityLivingBase_attemptTeleport(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if(((EntityLivingBase)(Object)this).getActivePotionEffect(BetterSurvivalWrapper.getAntiwarp()) != null) cir.setReturnValue(false);
    }
}