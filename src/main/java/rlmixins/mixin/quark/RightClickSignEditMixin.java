package rlmixins.mixin.quark;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.quark.RightClickSignEditHandler;
import vazkii.quark.tweaks.feature.RightClickSignEdit;

@Mixin(RightClickSignEdit.class)
public abstract class RightClickSignEditMixin {

    /**
     * Fix Quark configuration for Right Click Sign Edit.
     * This is done by syncing the configuration with the client using packets.
     */
    @Inject(
            method = "onInteract",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_quarkRightClickSignEdit_onInteract(PlayerInteractEvent.RightClickBlock event, CallbackInfo ci) {
        if(!RightClickSignEditHandler.isEnabled) ci.cancel();
    }
}