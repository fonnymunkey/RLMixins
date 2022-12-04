package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentLuckMagnification;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentLuckMagnification.class)
public abstract class EnchantmentLuckMagnificationMixin {

    /**
     * Cancel all of LuckMagnifications handling to properly check offhand and use RLCombat crits
     */

    @Inject(
            method = "onCritical",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    public void rlmixins_soManyEnchantmentsEnchantmentLuckMagnification_onCritical(CriticalHitEvent e, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "HandleEnchant",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    public void rlmixins_soManyEnchantmentsEnchantmentLuckMagnification_handleEnchant(LootingLevelEvent fEvent, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "magnifyLuck",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    public void rlmixins_soManyEnchantmentsEnchantmentLuckMagnification_magnifyLuck(TickEvent.PlayerTickEvent e, CallbackInfo ci) {
        ci.cancel();
    }
}