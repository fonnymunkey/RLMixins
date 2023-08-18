package rlmixins.mixin.quark;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.quark.base.module.Feature;
import vazkii.quark.tweaks.feature.SpringySlime;

@Mixin(SpringySlime.class)
public abstract class SpringySlimeMixin {

    @Redirect(
            method = "onEntityCollision",
            at = @At(value = "INVOKE", target = "Lvazkii/quark/base/module/ModuleLoader;isFeatureEnabled(Ljava/lang/Class;)Z"),
            remap = false
    )
    private static boolean rlmixins_quarkSpringySlime_onEntityCollision(Class<? extends Feature> clazz) {
        return false;
    }
}