package rlmixins.mixin.itemphysics;

import com.creativemd.itemphysic.EventHandler;
import meldexun.reachfix.util.ReachFixUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EventHandler.class)
public abstract class EventHandlerMixin {

    @Inject(
            method = "getReachDistance",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_itemPhysicsEventHandler_getReachDistance(EntityPlayer player, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(ReachFixUtil.getBlockReach(player, EnumHand.MAIN_HAND));
    }
}
