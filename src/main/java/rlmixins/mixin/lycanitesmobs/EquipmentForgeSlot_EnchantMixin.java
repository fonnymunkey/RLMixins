package rlmixins.mixin.lycanitesmobs;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.container.EquipmentForgeSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import rlmixins.handlers.ConfigHandler;

/**
 * By cdstk
 */
@Mixin(EquipmentForgeSlot.class)
public abstract class EquipmentForgeSlot_EnchantMixin {

    @ModifyReturnValue(
            method = "isItemValid",
            at = @At(value = "RETURN", ordinal = 2)
    )
    private boolean rlmixins_lycanitesMobsEquipmentForgeSlot_isItemValid(boolean isCompleteEquipment, @Local(argsOnly = true) ItemStack itemStack) {
        return isCompleteEquipment && (!ConfigHandler.LYCANITESMOBS_CONFIG.enchantsPreventDisassembling || !itemStack.isItemEnchanted());
    }
}