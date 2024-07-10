package rlmixins.mixin.betternether;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import paulevs.betternether.blocks.BlockBNDoor;

import java.util.Random;

@Mixin(BlockBNDoor.class)
public abstract class BlockBNDoorMixin {

    /**
     * @author fonnymunkey
     * @reason fix doors being duped when broken
     */
    @Overwrite
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER ?
                Items.AIR :
                Item.getItemFromBlock((BlockBNDoor)(Object)this);
    }
}