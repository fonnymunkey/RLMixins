package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.item.ItemMyrmexEgg;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemMyrmexEgg.class)
public abstract class ItemMyrmexEggOffhandMixin {

    /**
     * Stop the myrmex egg from trying to manually delete the held item, it is not needed and causes issues when holding in offhand
     */
    @Redirect(
            method = "onItemUse",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;setInventorySlotContents(ILnet/minecraft/item/ItemStack;)V")
    )
    public void rlmixins_iceAndFireItemMyrmexEgg_onItemUse(InventoryPlayer instance, int i, ItemStack stack) {
        //Do nothing
    }
}
