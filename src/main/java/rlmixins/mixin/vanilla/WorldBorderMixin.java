package rlmixins.mixin.vanilla;

import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.vanilla.TimeCacheHandler;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin {

    @Redirect(
            method = "getDiameter",
            at = @At(value = "INVOKE", target = "Ljava/lang/System;currentTimeMillis()J")
    )
    public long rlmixins_vanillaWorldBorder_getDiameter() {
        return TimeCacheHandler.getCurrentTime();
    }
}