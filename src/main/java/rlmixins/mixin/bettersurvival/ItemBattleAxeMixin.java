package rlmixins.mixin.bettersurvival;

import com.mujmajnkraft.bettersurvival.items.ItemBattleAxe;
import net.minecraft.enchantment.EnumEnchantmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ItemBattleAxe.class)
public abstract class ItemBattleAxeMixin {

    @Redirect(
            method = "canApplyAtEnchantingTable",
            at = @At(value = "INVOKE", target = "Lcom/mujmajnkraft/bettersurvival/integration/SoManyEnchantmentsCompat;isCombatAxeSMEEnchant(Lnet/minecraft/enchantment/EnumEnchantmentType;)Z"),
            remap = false
    )
    private boolean rlmixins_betterSurvivalItemBattleAxe_canApplyAtEnchantingTable(EnumEnchantmentType type) {
        return false;
    }
}