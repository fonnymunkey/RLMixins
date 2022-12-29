package rlmixins.handlers.chunkanimator;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChunkAnimatorCooldownHandler {
    private static int cooldown = 0;

    public static void startCooldown() {
        cooldown = 10;
    }

    public static int getCooldown() {
        return cooldown;
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START || cooldown <= 0) return;
        cooldown--;
    }
}