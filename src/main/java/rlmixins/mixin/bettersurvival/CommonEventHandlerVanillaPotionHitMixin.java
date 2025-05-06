package rlmixins.mixin.bettersurvival;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mujmajnkraft.bettersurvival.eventhandlers.CommonEventHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CommonEventHandler.class)
public abstract class CommonEventHandlerVanillaPotionHitMixin {

    // To be rehandled in vanilla
    // rlmixins.mixin.vanilla.EntityLivingBaseBSPotionHitsMixin
    @ModifyExpressionValue(
            method = "onAttack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;getInteger(Ljava/lang/String;)I"),
            remap = true
    )
    public int rlmixins_betterSurvivalCommonEventHandler_onAttack(int original){
        return 0;
    }
}
