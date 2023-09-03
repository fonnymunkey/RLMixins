package rlmixins.handlers.betterquestingunofficial;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import rlmixins.mixin.betterquestingunofficial.PlayerContainerListenerAccessor;

public class ListenMapHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if(event.player == null || event.player.world.isRemote) return;
        PlayerContainerListenerAccessor.getListenMap().entrySet()
                .removeIf(entry ->  event.player == ((PlayerContainerListenerAccessor)entry.getValue()).getPlayer());
    }
}
