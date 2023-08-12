package rlmixins.mixin.vanilla;

import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldServer.class)
public abstract class WorldServerSleepMixin {

    @Redirect(
            method = "wakeAllPlayers",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;resetRainAndThunder()V")
    )
    public void rlmixins_vanillaWorldServer_wakeAllPlayers(WorldServer instance) {
        //noop
    }
}