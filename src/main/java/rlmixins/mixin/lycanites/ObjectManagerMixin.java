package rlmixins.mixin.lycanites;

import com.lycanitesmobs.ObjectManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ObjectManager.class)
public abstract class ObjectManagerMixin {

    @Redirect(
            method = "getEffect",
            at = @At(value = "INVOKE", target = "Ljava/lang/String;toLowerCase()Ljava/lang/String;"),
            remap = false
    )
    private static String rlmixins_lycanitesMobsObjectManager_getEffect(String instance) {
        return instance;//Don't run toLowerCase on everything when EffectAura is the only non-final string
    }
}