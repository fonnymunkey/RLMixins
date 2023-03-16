package rlmixins.mixin.switchbow;

import de.Whitedraco.switchbow.gui.ContainerQuiver;
import de.Whitedraco.switchbow.gui.InventoryItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ContainerQuiver.class)
public abstract class ContainerQuiverMixin {

    @Shadow(remap = false) private ItemStack HoldItem;
    @Shadow(remap = false) private InventoryItems inventoryQuiver;

    /**
     * @author fonnymunkey
     * @reason fix interacting with gui while quiver is not in inventory
     */
    @Overwrite
    public boolean canInteractWith(EntityPlayer player) { return this.inventoryQuiver.isUsableByPlayer(player) && !this.HoldItem.isEmpty() && player.inventory.hasItemStack(this.HoldItem); }
}