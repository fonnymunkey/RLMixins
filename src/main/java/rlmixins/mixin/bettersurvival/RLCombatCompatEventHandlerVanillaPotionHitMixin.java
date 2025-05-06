package rlmixins.mixin.bettersurvival;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mujmajnkraft.bettersurvival.integration.RLCombatCompatEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RLCombatCompatEventHandler.class)
public abstract class RLCombatCompatEventHandlerVanillaPotionHitMixin {

    // To be rehandled in vanilla
    // rlmixins.mixin.vanilla.EntityLivingBaseBSPotionHitsMixin
    @ModifyExpressionValue(
            method = "onDamageModifyPost",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;getInteger(Ljava/lang/String;)I"),
            remap = true
    )
    public int rlmixins_betterSurvivalRLCombatCompatEventHandler_onDamageModifyPost(int original){
        return 0;
    }
}
