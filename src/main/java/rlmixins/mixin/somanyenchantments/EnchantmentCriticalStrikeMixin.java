package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_3_0.EnchantmentCriticalStrike;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentCriticalStrike.class)
public abstract class EnchantmentCriticalStrikeMixin {

    /**
     * Don't do anything here, handle in rlmixins.handlers.EventHandler for offhand compat
     */
    @Inject(
            method = "onCritical",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsEnchantmentCriticalStrike_onCritical(CriticalHitEvent e, CallbackInfo ci) {
        ci.cancel();
    }
}