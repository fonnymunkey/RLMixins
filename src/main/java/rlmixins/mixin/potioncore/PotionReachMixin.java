package rlmixins.mixin.potioncore;

import com.tmtravlr.potioncore.potion.PotionReach;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PotionReach.class)
public abstract class PotionReachMixin {

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(doubleValue = 1.0D)
    )
    private static double rlmixins_potioncorePotionReach_clinit(double constant) {
        return 0.5D;
    }
}