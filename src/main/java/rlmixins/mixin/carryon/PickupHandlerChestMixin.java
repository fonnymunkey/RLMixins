package rlmixins.mixin.carryon;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.ILootContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tschipp.carryon.common.handler.PickupHandler;

@Mixin(PickupHandler.class)
public abstract class PickupHandlerChestMixin {

    @Inject(
            method = "canPlayerPickUpBlock",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_carryOnPickupHandler_canPlayerPickupBlock(EntityPlayer player, TileEntity tile, World world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(world.getBlockState(pos).getBlock() instanceof BlockChest && tile instanceof TileEntityChest && ((ILootContainer)tile).getLootTable() != null) cir.setReturnValue(false);
    }
}