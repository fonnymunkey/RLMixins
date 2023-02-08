package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHippogryph.class)
public abstract class EntityHippogryphMixin {

    @Inject(
            method = "dropArmor",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_iceAndFireEntityHippogryph_dropArmor(CallbackInfo ci) {
        ci.cancel();
    }
}