package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.item.ItemShieldCobalt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemShieldCobalt.class)
public abstract class ItemShieldCobaltMixin {

    @ModifyConstant(
            method = "getAttributeModifiers",
            constant = @Constant(doubleValue = 10.0),
            remap = false
    )
    public double rlmixins_bountifulBaublesItemShieldCobalt_getAttributeModifiers(double constant) {
        return 1000.0;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(doubleValue = 10.0)
    )
    private static double rlmixins_bountifulBaublesItemShieldCobalt_clinit(double constant) {
        return 1000.0;
    }
}