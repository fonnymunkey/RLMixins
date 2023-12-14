package rlmixins.mixin.dummy;

import boni.dummy.client.RenderFloatingNumber;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RenderFloatingNumber.class)
public abstract class RenderFloatingNumberMixin {

    @ModifyConstant(
            method = "doRender(Lboni/dummy/EntityFloatingNumber;DDDFF)V",
            constant = @Constant(floatValue = 2.0F),
            remap = false
    )
    private static float rlmixins_dummyDoRender_constant(float constant) {
        return 1.0F;
    }
}