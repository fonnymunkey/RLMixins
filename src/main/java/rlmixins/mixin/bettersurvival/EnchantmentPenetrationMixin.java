package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.enchantments.EnchantmentPenetration;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentPenetration.class)
public abstract class EnchantmentPenetrationMixin {

    @Inject(
            method = "dealPiercingDamage",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_betterSurvivalEnchantmentPenetration_dealPiercingDamage(EntityLivingBase attacker, EntityLivingBase target, float amount, CallbackInfo ci) {
        ci.cancel();
    }
}