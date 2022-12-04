package rlmixins.mixin.reskillable;

import codersafterdark.reskillable.skill.defense.TraitUndershirt;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TraitUndershirt.class)
public abstract class TraitUndershirtMixin {

    /**
     * Cancel handling in order to properly handle it with FirstAid compat
     */
    @Inject(
            method = "onHurt",
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    public void rlmixins_reskillableTraitUndershirt_onHurt(LivingHurtEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}