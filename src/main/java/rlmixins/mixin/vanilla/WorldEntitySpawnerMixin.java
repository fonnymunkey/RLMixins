package rlmixins.mixin.vanilla;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldEntitySpawner.class)
public abstract class WorldEntitySpawnerMixin {

    @Redirect(
            method = "findChunksForSpawning",
            at = @At(value="INVOKE",target = "Lnet/minecraft/world/WorldServer;isAnyPlayerWithinRangeAt(DDDD)Z")
    )
    public boolean rlmixins_vanillaWorldEntitySpawner_findChunksForSpawning(WorldServer instance, double x, double y, double z, double range) {
        int x1 = MathHelper.floor(x);
        int z1 = MathHelper.floor(z);
        
        if(!((WorldInvoker)instance).invokeIsAreaLoaded(x1 - 32, 0, z1 - 32, x1 + 32, 0, z1 + 32, true)) return true;
        
        return instance.isAnyPlayerWithinRangeAt(x, y, z, range);
    }
}