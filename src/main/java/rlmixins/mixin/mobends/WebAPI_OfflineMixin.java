package rlmixins.mixin.mobends;

import goblinbob.mobends.core.WebAPI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WebAPI.class)
public abstract class WebAPI_OfflineMixin {

    @Inject(
            method = "getOfficialAnimationEditorUrl",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void rlmixins_moBendsWebAPI_getOfficialAnimationEditorUrl(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(null);
    }
}