package rlmixins.mixin.vanilla;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.chunkanimator.ChunkAnimatorCooldownHandler;

@Mixin(GameSettings.class)
public abstract class GameSettingsMixin {

    @Inject(
            method = "setOptionValue",
            at = @At("HEAD")
    )
    public void rlmixins_vanillaGameSettings_setOptionValue(GameSettings.Options settingsOption, int value, CallbackInfo ci) {
        if(settingsOption == GameSettings.Options.GRAPHICS ||
                settingsOption == GameSettings.Options.AMBIENT_OCCLUSION ||
                settingsOption == GameSettings.Options.USE_VBO) {
            ChunkAnimatorCooldownHandler.startCooldown();
        }
    }

    @Inject(
            method = "setOptionFloatValue",
            at = @At("HEAD")
    )
    public void rlmixins_vanillaGameSettings_setOptionFloatValue(GameSettings.Options settingsOption, float value, CallbackInfo ci) {
        if(settingsOption == GameSettings.Options.RENDER_DISTANCE) ChunkAnimatorCooldownHandler.startCooldown();
    }
}