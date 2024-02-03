package rlmixins.mixin.vanilla;

import net.minecraft.advancements.AdvancementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AdvancementManager.class)
public abstract class AdvancementManagerNukeMixin {

    @Inject(
            method = "reload",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_vanillaAdvancementManager_reload(CallbackInfo ci) {
        ci.cancel();
    }
}