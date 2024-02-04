package rlmixins.mixin.vanilla;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(World.class)
public abstract class WorldSpawnChunksMixin {

    @Inject(
            method = "isSpawnChunk",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_vanillaWorld_isSpawnChunk_inject(int x, int z, CallbackInfoReturnable<Boolean> cir) {
        if(ForgeConfigHandler.server.spawnChunkRadius == -1) cir.setReturnValue(false);
    }

    @ModifyConstant(
            method = "isSpawnChunk",
            constant = @Constant(intValue = 128)
    )
    public int rlmixins_vanillaWorld_isSpawnChunk_modify_0(int constant) {
        return ForgeConfigHandler.server.spawnChunkRadius * 16;
    }

    @ModifyConstant(
            method = "isSpawnChunk",
            constant = @Constant(intValue = -128)
    )
    public int rlmixins_vanillaWorld_isSpawnChunk_modify_1(int constant) {
        return ForgeConfigHandler.server.spawnChunkRadius * -16;
    }
}