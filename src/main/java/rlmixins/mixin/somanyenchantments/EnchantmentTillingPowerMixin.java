package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentTillingPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentTillingPower.class)
public abstract class EnchantmentTillingPowerMixin {

    /**
     * Fix Plowing enchantment incorrectly calculating minimum enchantability causing book wyrm exploit
     */
    @Inject(
            method = "getMinEnchantability",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_soManyEnchantmentsEnchantmentTillingPower_getMinEnchantability(int par1, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(25 + (par1 - 1) * 15);
    }
}