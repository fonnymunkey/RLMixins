package rlmixins.mixin.reskillable;

import codersafterdark.reskillable.base.LevelLockHandler;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLockHandler.class)
public abstract class LevelLockHandlerIndirectMixin {

    @Inject(
            method = "hurtEvent",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_reskillableLevelLockHandler_hurtEvent(LivingAttackEvent event, CallbackInfo ci) {
        if(event.getEntity() != null && event.getEntity().equals(event.getSource().getTrueSource())) ci.cancel();
    }
}