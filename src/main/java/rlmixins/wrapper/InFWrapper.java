package rlmixins.wrapper;

import com.github.alexthe666.iceandfire.core.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public abstract class InFWrapper {

    public static ItemStack getDragonBowAmmo(EntityPlayer player) {
        if(isArrow(player.getHeldItem(EnumHand.OFF_HAND))) return player.getHeldItem(EnumHand.OFF_HAND);
        else if(isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) return player.getHeldItem(EnumHand.MAIN_HAND);
        else {
            for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);
                if(isArrow(itemstack)) return itemstack;
            }
            return ItemStack.EMPTY;
        }
    }

    private static boolean isArrow(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == ModItems.dragonbone_arrow;
    }
}