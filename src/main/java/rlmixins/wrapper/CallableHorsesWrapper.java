package rlmixins.wrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import tschipp.callablehorses.CallableHorses;
import tschipp.callablehorses.common.HorseManager;
import tschipp.callablehorses.common.capabilities.horseowner.IHorseOwner;
import tschipp.callablehorses.common.capabilities.storedhorse.IStoredHorse;
import tschipp.callablehorses.common.config.CallableHorsesConfig;
import tschipp.callablehorses.common.helper.HorseHelper;

public abstract class CallableHorsesWrapper {

    // https://github.com/Tschipp/CallableHorses/blob/master/src/main/java/tschipp/callablehorses/common/events/EntityEvents.java#L192
    public static void applyCallableHorseDeath(Entity entity){
        if (!entity.world.isRemote && entity instanceof AbstractHorse) {
            IStoredHorse horse = HorseHelper.getHorseCap(entity);
            if (horse.isOwned()) {
                EntityPlayer owner = HorseHelper.getPlayerFromUUID(horse.getOwnerUUID(), entity.world);
                if (owner != null) {
                    IHorseOwner horseOwner = HorseHelper.getOwnerCap(owner);
                    if (CallableHorsesConfig.settings.deathIsPermanent) {
                        horseOwner.clearHorse();
                        owner.sendMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.alert.death")));
                    } else {
                        AbstractHorse deadHorse = horseOwner.getHorseEntity(owner.world);
                        HorseManager.prepDeadHorseForRespawning(deadHorse);
                        horseOwner.setHorseNBT(deadHorse.serializeNBT());
                        horseOwner.setLastSeenPosition(BlockPos.ORIGIN);
                    }
                } else {
                    CallableHorses.LOGGER.debug(entity + " was marked as killed.");
                    HorseHelper.getWorldData(entity.world).markKilled(horse.getStorageUUID());
                }
            }
        }
    }
}
