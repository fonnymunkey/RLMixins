package rlmixins.wrapper;

import de.Whitedraco.switchbow.helper.ArrowItemStackEqual;
import de.Whitedraco.switchbow.helper.QuiverArrowHelper;
import de.Whitedraco.switchbow.helper.SwitchBowHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class SwitchbowWrapper {

    public static ItemStack getAmmo(EntityPlayer player, ItemStack bow) {
        if(ArrowItemStackEqual.containsArrow(QuiverArrowHelper.getArrowsInInvAndQuiver(player, player.inventory), SwitchBowHelper.getSelectionArrow(bow))) return SwitchBowHelper.getSelectionArrow(bow);
        else return ItemStack.EMPTY;
    }

}