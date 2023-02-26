package rlmixins.mixin.scalinghealth;

import net.minecraft.entity.EntityLivingBase;
import net.silentchaos512.scalinghealth.event.DifficultyHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DifficultyHandler.class)
public abstract class DifficultyHandlerMixin {

    @Inject(
            method = "canIncreaseEntityHealth",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getEntityAttribute(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;", shift = At.Shift.BEFORE),
            cancellable = true
    )
    private static void rlmixins_scalingHealthDifficultyHandler_canIncreaseEntityHealth(EntityLivingBase entity, CallbackInfoReturnable<Boolean> cir) {
        if(entity.isDead || entity.getHealth() <= 0.0) cir.setReturnValue(false);
    }
}