package rlmixins.mixin.quark;

import net.minecraftforge.client.event.RenderLivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.world.effects.PotionColorizer;

@Mixin(PotionColorizer.class)
public abstract class PotionColorizerClientMixin {

    @Inject(
            method = "colorize(Lnet/minecraftforge/client/event/RenderLivingEvent$Pre;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_quarkPotionColorizer_colorizePre(RenderLivingEvent.Pre event, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "colorize(Lnet/minecraftforge/client/event/RenderLivingEvent$Post;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_quarkPotionColorizer_colorizePost(RenderLivingEvent.Post event, CallbackInfo ci) {
        ci.cancel();
    }
}