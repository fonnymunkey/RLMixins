package rlmixins.mixin.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import rlmixins.wrapper.IWorldServerMixin;

/**
 * Based on Entity Tracker patch created by mrgrim and EigenCraft Unofficial Patch https://github.com/mrgrim/MUP/blob/master/src/main/java/org/gr1m/mc/mup/bugfix/mc92916/mixin/MixinWorldServer.java
 */
@Mixin(WorldServer.class)
public abstract class WorldServerMixin extends World implements IWorldServerMixin
{
    public WorldServerMixin(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId, Profiler profilerIn) {
        super(saveHandlerIn, info, DimensionType.getById(dimensionId).createDimension(), profilerIn, false);
    }

    public void prepareLeaveDimension(Entity entityIn) {
        entityIn.lastTickPosX = entityIn.posX;
        entityIn.lastTickPosY = entityIn.posY;
        entityIn.lastTickPosZ = entityIn.posZ;
        entityIn.prevRotationYaw = entityIn.rotationYaw;
        entityIn.prevRotationPitch = entityIn.rotationPitch;
    }
}