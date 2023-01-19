package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.util.handlers.SRPEventHandlerBus;
import net.minecraftforge.client.event.MouseEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SRPEventHandlerBus.class)
public abstract class SRPEventHandlerBusPacketMixin {

    @Inject(
            method = "onEvent(Lnet/minecraftforge/client/event/MouseEvent;)V",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_srparasitesEventHandlerBus_onEvent(MouseEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}
