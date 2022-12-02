package rlmixins.mixin.foodexpansion;

import lellson.foodexpansion.items.ItemFoodBasic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFoodBasic.class)
public abstract class ItemFoodBasicMixin {

    @Shadow(remap = false) private ItemStack returnItem;

    @Inject(
            method = "onItemUseFinish",
            at = @At("RETURN"),
            cancellable = true
    )
    public void rlmixins_foodExpansionItemFoodBasic_onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity, CallbackInfoReturnable<ItemStack> cir) {
        if(this.returnItem != null) {
            if(entity instanceof EntityPlayer) {
                ((EntityPlayer)entity).inventory.addItemStackToInventory(new ItemStack(this.returnItem.getItem(), 1));
            }
            else entity.dropItem(this.returnItem.getItem(), 1);
        }
        cir.setReturnValue(stack);
    }
}