package rlmixins.handlers;

import lykrast.defiledlands.common.init.ModItems;
import net.minecraft.item.Item;

public abstract class DefiledHandler {

    public static Item getGoldenWyrmScale() {
        return ModItems.bookWyrmScaleGolden;
    }
}