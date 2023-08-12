package rlmixins.mixin.betterquesting;

import bq_standard.handlers.PlayerContainerListener;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.HashMap;
import java.util.UUID;

@Mixin(PlayerContainerListener.class)
public interface PlayerContainerListenerAccessor {

    @Accessor(value = "LISTEN_MAP", remap = false)
    static HashMap<UUID, PlayerContainerListener> getListenMap() { throw new IllegalStateException("Mixin failed to apply"); }

    @Accessor(value = "player", remap = false)
    EntityPlayer getPlayer();
}