package rlmixins.mixin.vanilla;

import net.minecraft.world.ServerWorldEventHandler;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerWorldEventHandler.class)
public interface ServerWorldEventHandlerAccessor {
    @Accessor("world")
    WorldServer getWorld();
}