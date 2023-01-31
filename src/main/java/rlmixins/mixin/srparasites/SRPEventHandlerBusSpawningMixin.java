package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.util.handlers.SRPEventHandlerBus;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SRPEventHandlerBus.class)
public abstract class SRPEventHandlerBusSpawningMixin {

    @Inject(
            method = "worldTick",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_srParasitesSRPEventHandlerBus_worldTick(TickEvent.WorldTickEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}