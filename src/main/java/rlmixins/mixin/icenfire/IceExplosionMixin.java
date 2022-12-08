package rlmixins.mixin.icenfire;

import com.github.alexthe666.iceandfire.entity.IceExplosion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(IceExplosion.class)
public abstract class IceExplosionMixin {

    /**
     * Fixes too many particles spawning, and changes the fire particle to the snow particle
     */
    @Redirect(
            method = "doExplosionB",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V")
    )
    public void rlmixins_iceAndFireIceExplosion_doExplosionB(World instance, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int[] parameters) {
        if(particleType.equals(EnumParticleTypes.FLAME)) {
            if(instance.rand.nextFloat() < ForgeConfigHandler.server.iceDragonExplosionSnow) return;
            instance.spawnParticle(EnumParticleTypes.SNOWBALL, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        }
        else {
            if(instance.rand.nextFloat() < ForgeConfigHandler.server.iceDragonExplosionSmoke) return;
            instance.spawnParticle(EnumParticleTypes.CLOUD, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        }
    }
}