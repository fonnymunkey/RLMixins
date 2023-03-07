package rlmixins.mixin.toolbelt;

import gigaherz.toolbelt.belt.ItemToolBelt;
import gigaherz.toolbelt.common.ContainerBelt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerBelt.class)
public abstract class ContainerBeltMixin extends Container {

    private ItemStack stack;
    private int blockedSlot;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void rlmixins_toolBeltContainerBelt_init(IInventory playerInventory, int blockedSlot, ItemStack heldItem, CallbackInfo ci) {
        this.stack = heldItem;
        this.blockedSlot = blockedSlot;
    }

    /**
     * @author fonnymunkey
     * @reason fix interacting with gui while belt is not in slot
     */
    @Overwrite
    public boolean canInteractWith(EntityPlayer player) { return !this.stack.isEmpty() && player.inventory.getStackInSlot(blockedSlot).equals(this.stack); }

    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player) {
        return slot >= 0 && this.getSlot(slot) != null && this.getSlot(slot).getStack().getItem() instanceof ItemToolBelt
                ? ItemStack.EMPTY
                : super.slotClick(slot, dragType, clickType, player);
    }
}