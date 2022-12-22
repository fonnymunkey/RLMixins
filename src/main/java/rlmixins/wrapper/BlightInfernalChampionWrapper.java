package rlmixins.wrapper;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import c4.champions.common.capability.CapabilityChampionship;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.silentchaos512.scalinghealth.event.BlightHandler;

public abstract class BlightInfernalChampionWrapper {
    //Vanilla does not appreciate non-coremod methods being mixin'd into it's classes directly

    public static boolean isEntityInfernal(EntityLivingBase entity) { return InfernalMobsCore.getMobModifiers(entity) != null; }

    public static boolean isEntityBlight(EntityLivingBase entity) {
        return BlightHandler.isBlight(entity);
    }

    public static boolean isEntityChampion(EntityLiving entity) { return CapabilityChampionship.getChampionship(entity) != null; }
}