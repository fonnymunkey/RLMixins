package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.FireExplosion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(FireExplosion.class)
public abstract class FireExplosionMixin {

    /**
     * Fixes too many particles spawning
     */
    @Redirect(
            method = "doExplosionB",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V")
    )
    public void rlmixins_iceAndFireFireExplosion_doExplosionB(World instance, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int[] parameters) {
        if(particleType.equals(EnumParticleTypes.FLAME)) {
            if(instance.rand.nextFloat() < ForgeConfigHandler.server.fireDragonExplosionFlame) return;
            instance.spawnParticle(EnumParticleTypes.FLAME, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        }
        else {
            if(instance.rand.nextFloat() < ForgeConfigHandler.server.fireDragonExplosionSmoke) return;
            instance.spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        }
    }
}