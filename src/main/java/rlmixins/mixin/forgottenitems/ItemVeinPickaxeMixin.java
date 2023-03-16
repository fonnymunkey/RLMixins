package rlmixins.mixin.forgottenitems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tschipp.forgottenitems.items.ItemVeinPickaxe;
import tschipp.forgottenitems.util.FIConfig;

import java.util.ArrayList;

@Mixin(ItemVeinPickaxe.class)
public abstract class ItemVeinPickaxeMixin {

    @Shadow(remap = false) public IBlockState mat;

    @Shadow(remap = false) public ArrayList<BlockPos> positions;

    @Shadow(remap = false) public abstract void addPositions(int i, World world);

    @Shadow(remap = false) public ArrayList<BlockPos> tempPositions;

    /**
     * @author fonnymunkey
     * @reason fix vein pickaxe mining tile entities and bypassing protection
     */
    @Overwrite(remap = false)
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        World world = player.world;
        if (!world.isRemote && !player.isCreative() && !player.isSneaking()) {
            if(!canMineEffectively(player, pos, stack)) return false;
            this.mat = world.getBlockState(pos);
            this.positions.add(pos);

            for(int i = 0; i < FIConfig.veinPickaxeRadius; ++i) {
                for(int j = 0; j < this.positions.size(); ++j) {
                    this.addPositions(j, world);
                }
                this.positions.addAll(this.tempPositions);
                this.tempPositions.clear();
            }

            int maxDam = stack.getMaxDamage();
            for(BlockPos position : this.positions) {
                if(!stack.isEmpty() && stack.getItemDamage() <= maxDam) {
                    if(canMineEffectively(player, position, stack)) {
                        ((EntityPlayerMP)player).interactionManager.tryHarvestBlock(position);
                    }
                }
            }

            this.positions.clear();
            this.tempPositions.clear();
            return true;
        } else {
            return false;
        }
    }

    private static boolean canMineEffectively(EntityPlayer player, BlockPos pos, ItemStack stack) {
        IBlockState state = player.world.getBlockState(pos);
        Block block = state.getBlock();

        if(block == Blocks.AIR) return false;
        if(player.world.getTileEntity(pos) != null) return false;

        for(String type : stack.getItem().getToolClasses(player.getHeldItemMainhand())) {
            if(block.isToolEffective(type, state) && block.getHarvestLevel(state) <= stack.getItem().getHarvestLevel(player.getHeldItemMainhand(), type, player, state)) {
                return true;
            }
        }
        return false;
    }
}