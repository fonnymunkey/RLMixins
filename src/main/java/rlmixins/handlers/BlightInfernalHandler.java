package rlmixins.handlers;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import net.minecraft.entity.EntityLivingBase;
import net.silentchaos512.scalinghealth.event.BlightHandler;

public abstract class BlightInfernalHandler {
    //Vanilla does not appreciate non-coremod methods being mixin'd into it's classes directly

    public static boolean isEntityInfernal(EntityLivingBase entity) { return InfernalMobsCore.getMobModifiers(entity) != null; }

    public static boolean isEntityBlight(EntityLivingBase entity) {
        return BlightHandler.isBlight(entity);
    }
}