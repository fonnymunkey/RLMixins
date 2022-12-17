package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_1_0.EnchantmentBlessedEdge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentBlessedEdge.class)
public abstract class EnchantmentBlessedEdgeMixin {

    @Inject(
            method = "HandleEnchant",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentBlessedEdge_handleEnchant(LivingHurtEvent fEvent, CallbackInfo ci) {
        ci.cancel();
    }
}