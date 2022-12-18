package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import rlmixins.wrapper.IWorldServerMixin;

/**
 * Based on Entity Tracker patch created by mrgrim and EigenCraft Unofficial Patch https://github.com/mrgrim/MUP/blob/master/src/main/java/org/gr1m/mc/mup/bugfix/mc92916/mixin/MixinPlayerList.java
 */
 @Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    //Vanilla.
    @Redirect(
            method = "transferEntityToWorld(Lnet/minecraft/entity/Entity;ILnet/minecraft/world/WorldServer;Lnet/minecraft/world/WorldServer;Lnet/minecraftforge/common/util/ITeleporter;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;updateEntityWithOptionalForce(Lnet/minecraft/entity/Entity;Z)V"),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=moving"),
                    to = @At(
                            value = "CONSTANT",
                            args = "stringValue=placing")))
    public void doPrepareLeaveDimension(WorldServer worldIn, Entity entityIn, boolean forceUpdate) {
        ((IWorldServerMixin) worldIn).prepareLeaveDimension(entityIn);
    }

    //Forge
    @Redirect(
            method = "transferEntityToWorld(Lnet/minecraft/entity/Entity;ILnet/minecraft/world/WorldServer;Lnet/minecraft/world/WorldServer;Lnet/minecraftforge/common/util/ITeleporter;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;updateEntityWithOptionalForce(Lnet/minecraft/entity/Entity;Z)V", ordinal = 0),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=placing"),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraftforge/common/util/ITeleporter;placeEntity(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;F)V", remap = false)))
    public void doPrepareLeaveDimensionForge(WorldServer worldIn, Entity entityIn, boolean forceUpdate) {
        ((IWorldServerMixin) worldIn).prepareLeaveDimension(entityIn);
    }
}