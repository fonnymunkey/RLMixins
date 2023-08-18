package rlmixins.mixin.xat;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.XatWrapper;
import xzeroair.trinkets.util.config.TrinketsConfigEvent;

@Mixin(TrinketsConfigEvent.class)
public abstract class TrinketsConfigEventMixin {

    @Inject(
            method = "onConfigChanged",
            at = @At(value = "INVOKE", target = "Lxzeroair/trinkets/util/TrinketsConfig;Save()V"),
            remap = false
    )
    public void rlmixins_xatTrinketsConfigEvent_onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event, CallbackInfo ci) {
        XatWrapper.refreshMap();
    }
}