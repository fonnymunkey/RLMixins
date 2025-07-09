package rlmixins.mixin.mobends;

import goblinbob.mobends.core.connection.PingTask;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PingTask.class)
public abstract class PingTask_OfflineMixin {

    @Inject(
            method = "sendPing",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void rlmixins_moBendsPingTask_sendPing(CallbackInfo ci) {
        ci.cancel();
    }
}