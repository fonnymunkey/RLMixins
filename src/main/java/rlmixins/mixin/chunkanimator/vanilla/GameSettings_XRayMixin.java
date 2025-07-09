package rlmixins.mixin.chunkanimator.vanilla;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.chunkanimator.ChunkAnimationHandler;

@Mixin(GameSettings.class)
public abstract class GameSettings_XRayMixin {

    @Inject(
            method = "setOptionValue",
            at = @At("HEAD")
    )
    private void rlmixins_vanillaGameSettings_setOptionValue(GameSettings.Options settingsOption, int value, CallbackInfo ci) {
        if(settingsOption == GameSettings.Options.GRAPHICS ||
                settingsOption == GameSettings.Options.AMBIENT_OCCLUSION ||
                settingsOption == GameSettings.Options.USE_VBO) {
            ChunkAnimationHandler.startCooldown();
        }
    }

    @Inject(
            method = "setOptionFloatValue",
            at = @At("HEAD")
    )
    private void rlmixins_vanillaGameSettings_setOptionFloatValue(GameSettings.Options settingsOption, float value, CallbackInfo ci) {
        if(settingsOption == GameSettings.Options.RENDER_DISTANCE) ChunkAnimationHandler.startCooldown();
    }
}