package rlmixins.mixin.icenfire;

import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tschipp.callablehorses.CallableHorses;
import tschipp.callablehorses.common.HorseManager;
import tschipp.callablehorses.common.capabilities.horseowner.IHorseOwner;
import tschipp.callablehorses.common.capabilities.storedhorse.IStoredHorse;
import tschipp.callablehorses.common.config.CallableHorsesConfig;
import tschipp.callablehorses.common.helper.HorseHelper;

@Mixin(com.github.alexthe666.iceandfire.event.EventLiving.class)
public class EventLivingStonedHorseKillMixin {

    /*
    *
    *   Consider this case a callable horse kill to prevent duping
    *   Based on https://github.com/Tschipp/CallableHorses/blob/master/src/main/java/tschipp/callablehorses/common/events/EntityEvents.java#L192
    *
     */
    @Inject(
            method = "onPlayerAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setDead()V")
    )
    private void rlmixins_iceAndFireEntityLiving_onPlayerAttack(AttackEntityEvent event, CallbackInfo ci){
        if(!event.getTarget().world.isRemote && event.getTarget() instanceof AbstractHorse){

            IStoredHorse horse = HorseHelper.getHorseCap(event.getTarget());
            if (horse.isOwned())
            {
                EntityPlayer owner = HorseHelper.getPlayerFromUUID(horse.getOwnerUUID(), event.getTarget().world);
                if (owner != null)
                {
                    IHorseOwner horseOwner = HorseHelper.getOwnerCap(owner);
                    if (CallableHorsesConfig.settings.deathIsPermanent)
                    {
                        horseOwner.clearHorse();
                        owner.sendMessage(new TextComponentString(TextFormatting.RED + I18n.translateToLocal("callablehorses.alert.death")));
                    } else
                    {
                        AbstractHorse deadHorse = horseOwner.getHorseEntity(owner.world);
                        HorseManager.prepDeadHorseForRespawning(deadHorse);
                        horseOwner.setHorseNBT(deadHorse.serializeNBT());
                        horseOwner.setLastSeenPosition(BlockPos.ORIGIN);
                    }

                } else
                {
                    CallableHorses.LOGGER.debug(event + " was marked as killed.");
                    HorseHelper.getWorldData(event.getTarget().world).markKilled(horse.getStorageUUID());
                }
            }

        }
    }
}
