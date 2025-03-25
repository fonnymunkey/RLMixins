package rlmixins.mixin.firstaid;

import com.llamalad7.mixinextras.sugar.Local;
import ichttt.mods.firstaid.FirstAidConfig;
import ichttt.mods.firstaid.FirstAidConfig.Overlay.OverlayMode;
import ichttt.mods.firstaid.api.damagesystem.AbstractDamageablePart;
import ichttt.mods.firstaid.client.util.HealthRenderUtils;
import ichttt.mods.firstaid.common.damagesystem.PlayerDamageModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerDamageModel.class)
public abstract class PlayerDamageModelMixin {
    @ModifyArg(
            method = "getMaxRenderSize",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;getStringWidth(Ljava/lang/String;)I")
    )
    String rlmixins_firstAidPlayerDamageModel_getMaxRenderSize_stringWidth(String input, @Local AbstractDamageablePart part) {
        if(part.getAbsorption() > 0)
            return input + " + " + HealthRenderUtils.TEXT_FORMAT.format(part.getAbsorption());
        return input;
    }

    @Redirect(
            method = "getMaxRenderSize",
            at = @At(value = "FIELD", target = "Lichttt/mods/firstaid/FirstAidConfig$Overlay;overlayMode:Lichttt/mods/firstaid/FirstAidConfig$Overlay$OverlayMode;"),
            remap = false
    )
    OverlayMode rlmixins_firstAidPlayerDamageModel_getMaxRenderSize_healthOverlayPlacement(FirstAidConfig.Overlay instance, @Local AbstractDamageablePart part) {
        if (instance.overlayMode == OverlayMode.HEARTS && HealthRenderUtils.drawAsString(part, false))
            return OverlayMode.NUMBERS;
        return instance.overlayMode;
    }
}
