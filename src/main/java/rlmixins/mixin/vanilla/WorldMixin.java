package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

/**
 * Based on Entity Chunk Updating patch created by mrgrim and EigenCraft Unofficial Patch https://github.com/mrgrim/MUP/blob/master/src/main/java/org/gr1m/mc/mup/bugfix/mc108469/mixin/MixinWorld.java
 */
@Mixin(World.class)
public abstract class WorldMixin {
    @Redirect(
            method = "updateEntityWithOptionalForce",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/World;getChunk(II)Lnet/minecraft/world/chunk/Chunk;", ordinal = 0),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/World;getChunk(II)Lnet/minecraft/world/chunk/Chunk;", ordinal = 1)),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;setPositionNonDirty()Z", ordinal = 0))
    public boolean alwaysLoadChunk(Entity entityIn) {
        return true;
    }
}