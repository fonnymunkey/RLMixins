package rlmixins.mixin.potioncore;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.tmtravlr.potioncore.potion.PotionInvert;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionInvert.class)
public abstract class PotionInvertMixin {

    @ModifyExpressionValue(
            method = "invertPotionEffects",
            at = @At(value = "INVOKE", target = "Ljava/util/HashMap;containsKey(Ljava/lang/Object;)Z"),
            remap = false
    )
    private static boolean rlmixins_potionCorePotionInvert_invertPotionEffects(boolean original, @Local PotionEffect effect){
        return original && !effect.getPotion().isBadEffect();
    }
}
