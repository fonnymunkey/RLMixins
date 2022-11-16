package rlmixins.mixin.lycanites;

import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import com.lycanitesmobs.core.item.special.ItemSoulContract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ItemSoulContract.class)
public abstract class ItemSoulContractMixin {

    /**
     * Fix SoulContract issues with offhand
     * Original fix from Meldexun
     * https://gitlab.com/Lycanite/LycanitesMobs/-/merge_requests/454
     */
    @Redirect(
            method = "bindSoul",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/InventoryPlayer;setInventorySlotContents(ILnet/minecraft/item/ItemStack;)V"
            )
    )
    public void rlmixins_lycanitesItemSoulContract_bindSoul(InventoryPlayer inventory, int item, ItemStack stack) {
        //Do nothing
    }

    /**
     * Fix SoulContract issues with offhand
     * Original fix from Meldexun
     * https://gitlab.com/Lycanite/LycanitesMobs/-/merge_requests/454
     * Also fixes Lycanite's not properly removing NBT tags
     */
    @Redirect(
            method = "transferSoul",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/InventoryPlayer;setInventorySlotContents(ILnet/minecraft/item/ItemStack;)V"
            ),
            slice = @Slice(
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getPlayerEntityByUUID(Ljava/util/UUID;)Lnet/minecraft/entity/player/EntityPlayer;")
            )
    )
    public void rlmixins_lycanitesItemSoulContract_transferSoulRedirect(InventoryPlayer inventory, int item, ItemStack stack) {
        //Lycanite's doesn't properly remove tags because of unique id's being handled differently
        NBTTagCompound nbt = ((ItemSoulContract)(Object)this).getTagCompound(stack);
        nbt.removeTag("ownerUUIDMost");
        nbt.removeTag("ownerUUIDLeast");
        nbt.removeTag("petEntryUUIDMost");
        nbt.removeTag("petEntryUUIDLeast");
        stack.setTagCompound(nbt);
    }

    /**
     * Fix SoulContract issues with offhand on success
     */
    @Inject(
            method = "transferSoul",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/InventoryPlayer;setInventorySlotContents(ILnet/minecraft/item/ItemStack;)V"
            ),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getPlayerEntityByUUID(Ljava/util/UUID;)Lnet/minecraft/entity/player/EntityPlayer;")
            ),
            cancellable = true
    )
    public void rlmixins_lycanitesItemSoulContract_transferSoulInject(ItemStack itemStack, EntityPlayer player, TameableCreatureEntity tameableCreatureEntity, UUID ownerUUID, UUID petEntryUUID, CallbackInfoReturnable<Boolean> cir) {
        itemStack.shrink(1);
        cir.setReturnValue(true);
    }
}