package rlmixins.mixin.mobends;

import goblinbob.mobends.core.connection.ConnectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectionManager.class)
public abstract class ConnectionManagerMixin {

    @Inject(
            method = "setup",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_moBendsConnectionManager_setup(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "fetchSettingsForPlayer",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_moBendsConnectionManager_fetchSettingsForPlayer(String playerName, CallbackInfo ci) {
        ci.cancel();
    }
}