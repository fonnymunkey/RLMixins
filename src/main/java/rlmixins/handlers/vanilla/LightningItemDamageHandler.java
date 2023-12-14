package rlmixins.handlers.vanilla;

import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LightningItemDamageHandler {

    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event) {
        if(event.getEntity() instanceof EntityItem) event.setCanceled(true);
    }
}