package rlmixins.mixin.locks;

import melonslise.locks.common.container.KeyRingContainer;
import melonslise.locks.common.item.KeyRingItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(KeyRingContainer.class)
public abstract class KeyRingContainerMixin extends Container {

    @Shadow(remap = false) @Final public ItemStack stack;

    /**
     * @author fonnymunkey
     * @reason fix interacting with gui while ring is not in inventory
     */
    @Overwrite
    public boolean canInteractWith(EntityPlayer player) { return !this.stack.isEmpty() && player.inventory.hasItemStack(this.stack); }

    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player) {
        return slot >= 0 && this.getSlot(slot) != null && this.getSlot(slot).getStack().getItem() instanceof KeyRingItem
                ? ItemStack.EMPTY
                : super.slotClick(slot, dragType, clickType, player);
    }
}