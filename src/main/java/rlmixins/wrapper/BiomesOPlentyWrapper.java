package rlmixins.wrapper;

import biomesoplenty.common.block.BlockBOPHive;
import net.minecraft.block.Block;

public abstract class BiomesOPlentyWrapper {
    public static boolean isBlockHive(Block block) { return block instanceof BlockBOPHive; }

}
