package rlmixins.mixin.disenchanter;

import de.impelon.disenchanter.DisenchantingProperties;
import de.impelon.disenchanter.DisenchantingUtils;
import de.impelon.disenchanter.inventory.IDisenchantmentItemHandler;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(DisenchantingUtils.class)
public abstract class DisenchantingUtilsMixin {

    @Inject(
            method = "simulateDisenchantingInInventory(Lde/impelon/disenchanter/inventory/IDisenchantmentItemHandler;ZILde/impelon/disenchanter/DisenchantingProperties;FLjava/util/Random;)Lnet/minecraft/item/ItemStack;",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_disenchanterDisenchantingUtils_simulateDisenchantingInInventory(IDisenchantmentItemHandler inventory, boolean ignoreEnchantmentLoss, int startIndex, DisenchantingProperties properties, float power, Random random, CallbackInfoReturnable<ItemStack> cir) {
        if(properties == null || inventory == null) cir.setReturnValue(ItemStack.EMPTY);
    }
}