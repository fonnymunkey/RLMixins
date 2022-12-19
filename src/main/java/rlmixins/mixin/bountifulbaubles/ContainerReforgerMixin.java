package rlmixins.mixin.bountifulbaubles;

import cursedflames.bountifulbaubles.block.ContainerReforger;
import cursedflames.bountifulbaubles.util.GenericSlot;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;

@Mixin(ContainerReforger.class)
public abstract class ContainerReforgerMixin {

    /**
     * Fix being able to remove binding cursed armor in the reforging station
     */
    @Redirect(
            method = "addPlayerSlots",
            at = @At(
                    value = "NEW",
                    target = "cursedflames/bountifulbaubles/util/GenericSlot", ordinal = 0),
            remap = false
    )
    public GenericSlot rlmixins_bountifulBaublesContainerReforger_addPlayerSlots(IInventory inventoryIn, int index, int xPosition, int yPosition, Predicate<ItemStack> isValid, int maxSize) {
        return new GenericSlot(inventoryIn, index, xPosition, yPosition, isValid, maxSize) {
            @Override
            public boolean canTakeStack(EntityPlayer playerIn) {
                ItemStack itemstack = this.getStack();
                return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
            }
        };
    }
}