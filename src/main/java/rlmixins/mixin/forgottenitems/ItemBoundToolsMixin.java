package rlmixins.mixin.forgottenitems;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tschipp.forgottenitems.items.ItemBoundAxe;
import tschipp.forgottenitems.items.ItemBoundPickaxe;
import tschipp.forgottenitems.items.ItemBoundShovel;

@Mixin(value = {ItemBoundAxe.class, ItemBoundPickaxe.class, ItemBoundShovel.class})
public abstract class ItemBoundToolsMixin {

    @Redirect(
            method = "onItemRightClick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setTagCompound(Lnet/minecraft/nbt/NBTTagCompound;)V")
    )
    public void rlmixins_forgottenItemsItemBoundTools_onItemRightClick(ItemStack instance, NBTTagCompound nbt) {
        NBTTagCompound tag = instance.getTagCompound();
        if(tag == null) tag = new NBTTagCompound();
        tag.setString("owner", nbt.getString("owner"));
        instance.setTagCompound(tag);
    }
}