package rlmixins.handlers.quark;

import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.quark.management.entity.EntityChestPassenger;

public class ChestBoatHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public static void onTravelDimension(EntityTravelToDimensionEvent event) {
        if(event.isCanceled() && event.getEntity() instanceof EntityChestPassenger) {
            ((EntityChestPassenger)event.getEntity()).setDropItemsWhenDead(true);
        }
    }
}
