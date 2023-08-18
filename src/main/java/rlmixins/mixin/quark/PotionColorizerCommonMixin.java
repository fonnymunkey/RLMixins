package rlmixins.mixin.quark;

import net.minecraftforge.event.entity.living.PotionColorCalculationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.world.effects.PotionColorizer;

@Mixin(PotionColorizer.class)
public abstract class PotionColorizerCommonMixin {

    @Inject(
            method = "onStartTracking",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_quarkPotionColorizer_onStartTracking(PlayerEvent.StartTracking event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "onCalculateColor",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_quarkPotionColorizer_onCalculateColor(PotionColorCalculationEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}