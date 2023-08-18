package rlmixins.mixin.quark;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.vanity.client.emotes.EmoteHandler;

@Mixin(EmoteHandler.class)
public abstract class EmoteHandlerMixin {

    @Inject(
            method = "updateEmotes",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_quarkEmoteHandler_updateEmotes(Entity e, CallbackInfo ci) {
        ci.cancel();
    }
}