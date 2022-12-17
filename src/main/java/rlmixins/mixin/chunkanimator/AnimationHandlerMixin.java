package rlmixins.mixin.chunkanimator;

import lumien.chunkanimator.ChunkAnimator;
import lumien.chunkanimator.config.ChunkAnimatorConfig;
import lumien.chunkanimator.handler.AnimationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.chunkanimator.ChunkAnimatorCooldownHandler;

@Mixin(AnimationHandler.class)
public abstract class AnimationHandlerMixin {

    @Redirect(
            method = "setOrigin",
            at = @At(value = "INVOKE", target = "Llumien/chunkanimator/config/ChunkAnimatorConfig;disableAroundPlayer()Z"),
            remap = false
    )
    public boolean rlmixins_chunkAnimatorAnimationHandler_setOrigin(ChunkAnimatorConfig instance) {
        return ChunkAnimator.INSTANCE.config.disableAroundPlayer() || ChunkAnimatorCooldownHandler.getCooldown() > 0;
    }
}