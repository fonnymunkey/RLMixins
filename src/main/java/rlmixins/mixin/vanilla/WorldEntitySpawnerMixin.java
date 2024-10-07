package rlmixins.mixin.vanilla;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldEntitySpawner.class)
public abstract class WorldEntitySpawnerMixin {

    @Redirect(
            method = "findChunksForSpawning",
            at = @At(value="INVOKE",target = "Lnet/minecraft/world/WorldServer;isAnyPlayerWithinRangeAt(DDDD)Z")
    )
    private boolean disableLazySpawnsMixin(WorldServer instance, double x, double y, double z, double range){
        int i = (int) x;
        int j = (int) z;

        boolean areaIsFullyLoaded = instance.isAreaLoaded(new BlockPos(x,y,z), 32, true);
        if (!areaIsFullyLoaded)
            return false;

        return instance.isAnyPlayerWithinRangeAt(x, y, z, range);
    }

}