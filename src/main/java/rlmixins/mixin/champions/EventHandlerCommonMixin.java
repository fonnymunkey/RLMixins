package rlmixins.mixin.champions;

import c4.champions.common.EventHandlerCommon;
import c4.champions.common.capability.CapabilityChampionship;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EventHandlerCommon.class)
public abstract class EventHandlerCommonMixin {

    @Inject(
            method = "livingDeath",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/management/PlayerList;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V", shift = At.Shift.BEFORE),
            cancellable = true
    )
    public void rlmixins_championsEventHandlerCommon_livingDeath(LivingDeathEvent evt, CallbackInfo ci) {
        if(evt.getEntityLiving() instanceof EntityLiving && evt.getEntityLiving().getServer() != null && CapabilityChampionship.getChampionship((EntityLiving)evt.getEntityLiving()) != null) {
            evt.getEntityLiving().getServer().getPlayerList().sendMessage(
                    new TextComponentString(CapabilityChampionship.getChampionship((EntityLiving)evt.getEntityLiving()).getName())
                    .appendSibling(new TextComponentString(" "))
                    .appendSibling(evt.getEntityLiving().getCombatTracker().getDeathMessage())
            );
            ci.cancel();
        }
    }
}
