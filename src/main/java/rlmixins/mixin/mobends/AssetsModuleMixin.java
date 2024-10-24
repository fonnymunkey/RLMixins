package rlmixins.mixin.mobends;

import goblinbob.mobends.core.asset.AssetDefinition;
import goblinbob.mobends.core.asset.AssetsModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Collections;

@Mixin(AssetsModule.class)
public abstract class AssetsModuleMixin {

    @Redirect(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lgoblinbob/mobends/core/asset/AssetsModule;updateAssets()V", remap = false),
            remap = false
    )
    public void rlmixins_moBendsAssetsModule_init_update(AssetsModule instance) {
        //noop
    }

    @Inject(
            method = "updateAssets",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_moBendsAssetsModule_updateAssets(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "getAssets",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_moBendsAssetsModule_getAssets(CallbackInfoReturnable<Collection<AssetDefinition>> cir) {
        cir.setReturnValue(Collections.emptyList());
    }
}