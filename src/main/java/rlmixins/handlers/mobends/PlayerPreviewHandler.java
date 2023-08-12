package rlmixins.handlers.mobends;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import rlmixins.wrapper.MobendsWrapper;

public class PlayerPreviewHandler {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if(event.player == null || !event.player.world.isRemote) return;
        MobendsWrapper.clearPlayerPreview();
    }
}