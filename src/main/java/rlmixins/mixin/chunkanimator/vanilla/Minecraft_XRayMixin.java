package rlmixins.mixin.chunkanimator.vanilla;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.handlers.chunkanimator.ChunkAnimationHandler;

@Mixin(Minecraft.class)
public abstract class Minecraft_XRayMixin {

    @Inject(
            method = "processKeyF3",
            at = @At("HEAD")
    )
    private void rlmixins_vanillaMinecraft_processKeyF3(int auxKey, CallbackInfoReturnable<Boolean> cir) {
        if(auxKey == 30) ChunkAnimationHandler.startCooldown();
    }
}