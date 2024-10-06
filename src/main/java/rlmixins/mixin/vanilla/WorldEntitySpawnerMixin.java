package rlmixins.mixin.vanilla;

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

        StructureBoundingBox bb = new StructureBoundingBox(i - 32, 0, j - 32, i + 32, 0, j + 32);
        boolean areaIsFullyLoaded = instance.isAreaLoaded(bb, true);
        if (!areaIsFullyLoaded)
            return false;

        return instance.isAnyPlayerWithinRangeAt(x, y, z, range);
    }

}