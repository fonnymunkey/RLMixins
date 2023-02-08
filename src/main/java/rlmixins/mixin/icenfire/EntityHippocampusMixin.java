package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityHippocampus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHippocampus.class)
public abstract class EntityHippocampusMixin {

    @Inject(
            method = "dropArmor",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_iceAndFireEntityHippocampus_dropArmor(CallbackInfo ci) {
        ci.cancel();
    }
}