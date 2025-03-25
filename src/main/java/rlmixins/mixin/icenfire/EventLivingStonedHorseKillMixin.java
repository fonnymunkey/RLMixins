package rlmixins.mixin.icenfire;

import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.CallableHorsesWrapper;

@Mixin(com.github.alexthe666.iceandfire.event.EventLiving.class)
public class EventLivingStonedHorseKillMixin {

    /*
    *   Consider this case a callable horse kill to prevent duping
     */
    @Inject(
            method = "onPlayerAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setDead()V")
    )
    private void rlmixins_iceAndFireEntityLiving_onPlayerAttack(AttackEntityEvent event, CallbackInfo ci){
        CallableHorsesWrapper.applyCallableHorseDeath(event.getTarget());
    }
}
