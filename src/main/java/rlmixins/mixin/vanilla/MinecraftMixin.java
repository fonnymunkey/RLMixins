package rlmixins.mixin.vanilla;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.handlers.chunkanimator.ChunkAnimatorCooldownHandler;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Inject(
            method = "processKeyF3",
            at = @At("HEAD")
    )
    public void rlmixins_vanillaMinecraft_processKeyF3(int auxKey, CallbackInfoReturnable<Boolean> cir) {
        if(auxKey == 30) ChunkAnimatorCooldownHandler.startCooldown();
    }
}