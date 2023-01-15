package rlmixins.wrapper;

import c4.champions.common.capability.CapabilityChampionship;
import c4.champions.common.util.ChampionHelper;
import net.minecraft.entity.EntityLiving;

public abstract class ChampionWrapper {

    public static boolean isEntityChampion(EntityLiving entity) {
        return CapabilityChampionship.getChampionship(entity) != null && ChampionHelper.isElite(CapabilityChampionship.getChampionship(entity).getRank());
    }
}