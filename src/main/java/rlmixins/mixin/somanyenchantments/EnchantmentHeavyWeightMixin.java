package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_1_0.EnchantmentHeavyWeight;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentHeavyWeight.class)
public abstract class EnchantmentHeavyWeightMixin {

    @Inject(
            method = "HandleEnchant",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentHeavyWeight_handleEnchant(LivingEvent.LivingUpdateEvent fEvent, CallbackInfo ci) {
        ci.cancel();
    }
}