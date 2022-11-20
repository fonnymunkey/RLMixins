package rlmixins.mixin.vanilla;

import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*
@Mixin(PlayerInteractionManager.class)
public class PlayerInteractionManagerMixin {


      Fix breaking blocks too fast causing ghost blocks, based on patch by RandomPatches
      https://github.com/TheRandomLabs/RandomPatches/blob/1.12/src/main/java/com/therandomlabs/randompatches/patch/NetHandlerPlayServerPatch.java

    @Inject(
            method = "onBlockClicked",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;sendBlockBreakProgress(ILnet/minecraft/util/math/BlockPos;I)V")
    )
    public void rlmixins_vanillaPlayerInteractionManager_onBlockClicked(BlockPos pos, EnumFacing side, CallbackInfo ci) {
        ((PlayerInteractionManager)(Object)this).player.connection.sendPacket(new SPacketBlockChange(((PlayerInteractionManager)(Object)this).world, pos));
    }
}
*/