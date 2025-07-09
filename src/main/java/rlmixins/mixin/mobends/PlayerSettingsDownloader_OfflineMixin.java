package rlmixins.mixin.mobends;

import goblinbob.mobends.core.connection.PlayerSettingsDownloader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerSettingsDownloader.class)
public abstract class PlayerSettingsDownloader_OfflineMixin {

    @Inject(
            method = "run",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void rlmixins_moBendsPlayerSettingsDownloader_run(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "fetchSettingsForPlayer",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void rlmixins_moBendsPlayerSettingsDownloader_fetchSettingsForPlayer(String playerName, CallbackInfo ci) {
        ci.cancel();
    }
}