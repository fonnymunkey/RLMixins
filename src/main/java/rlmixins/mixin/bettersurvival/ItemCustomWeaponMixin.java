package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.items.ItemCustomWeapon;
import net.minecraft.enchantment.EnumEnchantmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemCustomWeapon.class)
public abstract class ItemCustomWeaponMixin {

    @Redirect(
            method = "canApplyAtEnchantingTable",
            at = @At(value = "INVOKE", target = "Lcom/mujmajnkraft/bettersurvival/integration/SoManyEnchantmentsCompat;isWeaponSMEEnchant(Lnet/minecraft/enchantment/EnumEnchantmentType;)Z"),
            remap = false
    )
    private boolean rlmixins_betterSurvivalItemCustomWeapon_canApplyAtEnchantingTable(EnumEnchantmentType type) {
        return false;
    }
}