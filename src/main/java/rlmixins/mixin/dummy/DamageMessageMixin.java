package rlmixins.mixin.dummy;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(targets = "boni.dummy.network.DamageMessage$MessageHandlerClient$1")
public abstract class DamageMessageMixin {

    @ModifyConstant(
            method = "run",
            constant = @Constant(floatValue = 2.0F),
            remap = false
    )
    private static float rlmixins_dummyDamageMessage_constant(float constant) {
        return 1.0F;
    }
}