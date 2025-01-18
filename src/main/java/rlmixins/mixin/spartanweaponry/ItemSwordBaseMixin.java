package rlmixins.mixin.spartanweaponry;

import com.oblivioussp.spartanweaponry.item.ItemSwordBase;
import net.minecraft.enchantment.EnumEnchantmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemSwordBase.class)
public abstract class ItemSwordBaseMixin {

    @Redirect(
            method = "canApplyAtEnchantingTable",
            at = @At(value = "INVOKE", target = "Lcom/oblivioussp/spartanweaponry/compat/sme/SMEHelper;isCombatAxeEnchantment(Lnet/minecraft/enchantment/EnumEnchantmentType;)Z"),
            remap = false
    )
    private boolean rlmixins_spartanWeaponryItemSwordBase_canApplyAtEnchantingTable(EnumEnchantmentType typeIn) {
        return false;
    }
}