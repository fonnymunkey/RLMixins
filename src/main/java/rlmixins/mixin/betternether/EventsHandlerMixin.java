package rlmixins.mixin.betternether;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.world.WorldEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.betternether.events.EventsHandler;

@Mixin(EventsHandler.class)
public abstract class EventsHandlerMixin {

    @Shadow(remap = false) static MinecraftServer server;

    @Inject(
            method = "onWorldLoad",
            at = @At(value = "INVOKE", target = "Lpaulevs/betternether/commands/CommandsRegister;register(Lnet/minecraft/command/ServerCommandManager;)V", shift = At.Shift.AFTER),
            remap = false
    )
    public void rlmixins_betterNetherEventsHandler_onWorldLoad(WorldEvent.Load event, CallbackInfo ci) {
        server = null;
    }
}