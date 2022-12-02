package rlmixins.mixin.vanilla;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.ServerWorldEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ServerWorldEventHandler.class)
public abstract class ServerWorldEventHandlerMixin {

    /**
     * Fixes particles not properly being spawned on clientside that are supposed to be
     * Based on patch by RandomPatches
     * https://github.com/TheRandomLabs/RandomPatches/blob/1.12/src/main/java/com/therandomlabs/randompatches/patch/ServerWorldEventHandlerPatch.java
     */
    @Inject(
            method = "spawnParticle(IZDDDDDD[I)V",
            at = @At("HEAD")
    )
    public void rlmixins_vanilla_serverWorldEventHandler_spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int[] parameters, CallbackInfo ci) {
        EnumParticleTypes particle = Objects.requireNonNull(EnumParticleTypes.getParticleFromId(particleID));
        if(particle == EnumParticleTypes.SPELL_MOB || particle == EnumParticleTypes.SPELL_MOB_AMBIENT)
            return;
        if(parameters.length == particle.getArgumentCount())
            ((ServerWorldEventHandlerAccessor)this).getWorld().spawnParticle(particle, xCoord, yCoord, zCoord, 0, xSpeed, ySpeed, zSpeed, 1.0, parameters);
    }
}