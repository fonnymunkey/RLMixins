package rlmixins.mixin.betternether;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import paulevs.betternether.blocks.BlockBNSlab;

import java.util.Random;

@Mixin(BlockBNSlab.class)
public abstract class BlockBNSlabMixin extends BlockSlab {

    @Unique
    private Block rlmixins$blockDrop = null;

    public BlockBNSlabMixin(Material materialIn) {
        super(materialIn);
    }

    /**
     * @author fonnymunkey
     * @reason fix double slabs not dropping items
     */
    @Overwrite
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.isDouble() ? Item.getItemFromBlock(this.rlmixins$blockDrop) : Item.getItemFromBlock(((BlockBNSlab)(Object)this));
    }

    /**
     * @author fonnymunkey
     * @reason fix double slabs not dropping items
     */
    @Overwrite
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return this.isDouble() ? new ItemStack(Item.getItemFromBlock(this.rlmixins$blockDrop)) : new ItemStack(((BlockBNSlab)(Object)this), 1, 0);
    }

    /**
     * @author fonnymunkey
     * @reason fix double slabs not dropping items
     */
    @Overwrite(remap = false)
    public void setDrop(Block block) {
        this.rlmixins$blockDrop = block;
    }
}