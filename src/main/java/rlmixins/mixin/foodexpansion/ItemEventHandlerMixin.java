package rlmixins.mixin.foodexpansion;

import lellson.foodexpansion.items.ItemEventHandler;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ItemEventHandler.class)
public abstract class ItemEventHandlerMixin {
    /**
     * @author Nischhelm
     * @reason FoodExpansionConfig.bowlStackSizeItems list is empty in RLCraft anyway, we handle giving bowls back differently than the switcheroo foodexpansion is attempting here
     */
    @Overwrite(remap = false)
    public void itemUsed(LivingEntityUseItemEvent.Finish event) {
        //no op
    }

}