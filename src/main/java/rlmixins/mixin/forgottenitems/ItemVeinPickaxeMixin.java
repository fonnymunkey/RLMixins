package rlmixins.mixin.forgottenitems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tschipp.forgottenitems.items.ItemVeinPickaxe;

@Mixin(ItemVeinPickaxe.class)
public abstract class ItemVeinPickaxeMixin {

    /**
     * @author fonnymunkey
     * @reason fix vein pickaxe handling, passed to VeinPickaxeHandler
     */
    @Overwrite(remap = false)
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        return false;
    }
}