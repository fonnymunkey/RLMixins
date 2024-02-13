package rlmixins.mixin.vanilla;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(World.class)
public abstract class WorldFlammableMixin {

    @Unique
    private BlockPos.PooledMutableBlockPos rlmixins$blockpos = null;

    @Inject(
            method = "isFlammableWithin",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void rlmixins_vanillaWorld_isFlammableWithin_inject0(AxisAlignedBB bb, CallbackInfoReturnable<Boolean> cir, int j2, int k2, int l2, int i3, int j3, int k3, BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos, int l3, int i4, int j4) {
        this.rlmixins$blockpos = blockpos$pooledmutableblockpos;
    }

    @Redirect(
            method = "isFlammableWithin",
            at = @At(value = "NEW", target = "(III)Lnet/minecraft/util/math/BlockPos;")
    )
    public BlockPos rlmixins_vanillaWorld_isFlammableWithin_redirect(int x, int y, int z) {
        return this.rlmixins$blockpos;
    }

    @Inject(
            method = "isFlammableWithin",
            at = @At("RETURN")
    )
    public void rlmixins_vanillaWorld_isFlammableWithin_inject1(AxisAlignedBB bb, CallbackInfoReturnable<Boolean> cir) {
        this.rlmixins$blockpos = null;
    }
}