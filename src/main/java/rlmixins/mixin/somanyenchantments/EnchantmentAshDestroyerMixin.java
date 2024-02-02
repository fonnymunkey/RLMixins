package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_3_0.EnchantmentAshDestroyer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentAshDestroyer.class)
public abstract class EnchantmentAshDestroyerMixin {

    @Inject(
            method = "HandleEnchant",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentAshDestroyer_handleEnchant(LivingDamageEvent fEvent, CallbackInfo ci) {
        ci.cancel();
    }
}