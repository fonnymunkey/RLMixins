package rlmixins.mixin.vanilla;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ContainerBeacon.class)
public abstract class ContainerBeaconMixin {
    @WrapWithCondition(
            method = "onContainerClosed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;")
    )
    private boolean rlmixins_vanillaContainerMerchant_containerClosed(EntityPlayer player, ItemStack stack, boolean unused) {
        if (!player.isEntityAlive() || player instanceof EntityPlayerMP && ((EntityPlayerMP) player).hasDisconnected())
            return true; //original behavior of dropping the items
        else {
            player.inventory.placeItemBackInInventory(player.world, stack);
            return false;
        }
    }
}
