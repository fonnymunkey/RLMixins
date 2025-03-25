package rlmixins.mixin.vanilla;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSoup.class)
public abstract class ItemSoupMixin extends Item {
    @Inject(
            method = "onItemUseFinish",
            at = @At("HEAD"),
            cancellable = true
    )
    private void rlmixins_vanillaItemSoup_onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity, CallbackInfoReturnable<ItemStack> cir){
        //With soups being stackable we need to add the bowl to player inventory instead of replacing the stack
        if(!entity.world.isRemote)
            if(entity instanceof EntityPlayer)
                ((EntityPlayer) entity).inventory.addItemStackToInventory(new ItemStack(Items.BOWL));
            else
                entity.dropItem(Items.BOWL, 1);
        cir.setReturnValue(super.onItemUseFinish(stack, worldIn,entity));
    }
}