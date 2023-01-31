package rlmixins.wrapper;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import svenhjol.charm.crafting.block.BlockCrate;
import svenhjol.charm.enchanting.feature.CurseBreak;

public abstract class CharmWrapper {
    public static Enchantment getCurseBreak() { return CurseBreak.enchantment; }
    public static boolean isBlockCrate(Block block) { return block instanceof BlockCrate; }
}
