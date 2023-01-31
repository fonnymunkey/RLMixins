package rlmixins.mixin.vanilla;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.wrapper.CharmWrapper;

@Mixin(TileEntityShulkerBox.class)
public abstract class TileEntityShulkerBoxMixin {

    @Inject(
            method = "canInsertItem",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_vanillaTileEntityShulkerBox_canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction, CallbackInfoReturnable<Boolean> cir) {
        if(CharmWrapper.isBlockCrate(Block.getBlockFromItem(itemStackIn.getItem()))) cir.setReturnValue(false);
    }
}