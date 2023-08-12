package rlmixins.handlers.vanilla;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TimeCacheHandler {

    private static long currentTime = System.currentTimeMillis();

    public static long getCurrentTime() { return currentTime; }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if(event.phase != TickEvent.Phase.START) return;
        currentTime = System.currentTimeMillis();
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.START) return;
        currentTime = System.currentTimeMillis();
    }
}
