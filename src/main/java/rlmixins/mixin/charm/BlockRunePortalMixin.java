package rlmixins.mixin.charm;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(svenhjol.charm.world.block.BlockRunePortal.class)
public abstract class BlockRunePortalMixin {

    /*
    *
    *   Prevent non player entities from being processed
    *   They would originally be soft killed and not be considered a callable horse kill for example
    *
     */
    @Inject(
            method = "onEntityCollision",
            at = @At("HEAD"),
            cancellable = true
    )
    private void rlmixins_charmRunePortal_onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn, CallbackInfo ci){
        if(!worldIn.isRemote && !(entityIn instanceof EntityPlayer)) ci.cancel();
    }
}
