package rlmixins.mixin.betterfoliage;

import mods.octarinecore.common.GeometryKt;
import mods.octarinecore.common.Int3;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GeometryKt.class)
public abstract class GeometryKtMixin {

    @Inject(
            method = "plus",
            at = @At(value = "NEW", target = "(III)Lnet/minecraft/util/math/BlockPos;", shift = At.Shift.BEFORE),
            cancellable = true
    )
    private static void rlmixins_betterFoliageGeometryKt_plus(BlockPos $this$plus, Int3 other, CallbackInfoReturnable<BlockPos> cir) {
        if(other.getX() == 0 && other.getY() == 0 && other.getZ() == 0) cir.setReturnValue($this$plus);
    }
}