package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.block.ContainerReforger;
import cursedflames.bountifulbaubles.util.GenericSlot;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(ContainerReforger.class)
public abstract class ContainerReforgerMixin {

    @Redirect(
            method = "addPlayerSlots",
            at = @At(value = "NEW", target = "Lcursedflames/bountifulbaubles/util/GenericSlot;<init>(Lnet/minecraft/inventory/IInventory;IIILjava/util/function/Predicate;I)V", remap = false),
            remap = false
    )
    public GenericSlot rlmixins_bountifulBaublesContainerReforger_addPlayerSlots(IInventory inventoryIn, int index, int xPosition, int yPosition, Predicate<ItemStack> isValid, int maxSize) {
        return new GenericSlot(inventoryIn, index, xPosition, yPosition, isValid.and(stack -> !EnchantmentHelper.hasBindingCurse(stack)), maxSize);
    }
}