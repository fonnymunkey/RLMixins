package rlmixins.mixin.reskillable;

import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.RequirementHolder;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.RLMixins;

@Mixin(PlayerData.class)
public abstract class PlayerDataMixin {

    /**
     * Fixes Reskillable removing and potentially deleting Baubles when a player returns from the End
     */
    @Inject(
            method = "matchStats",
            at = @At(value = "RETURN"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_reskillablePlayerData_matchStats(RequirementHolder holder, CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue() && ((RequirementCacheInvoker)(((PlayerData)(Object)this).getRequirementCache())).invokeGetPlayer()==null) {
            RLMixins.LOGGER.log(Level.WARN, "RLMixins: Detected player " + ((PlayerData)(Object)this).playerWR.get().getName() + " missing from worlds for Reskillable check, passing check. This should only happen when a player is returning from the End, if you believe it is in error, please report it.");
            cir.setReturnValue(true);
        }
    }
}
