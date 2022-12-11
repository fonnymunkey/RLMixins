package rlmixins.wrapper;

import lykrast.defiledlands.common.init.ModItems;
import net.minecraft.item.Item;

public abstract class DefiledWrapper {

    public static Item getGoldenWyrmScale() {
        return ModItems.bookWyrmScaleGolden;
    }
}