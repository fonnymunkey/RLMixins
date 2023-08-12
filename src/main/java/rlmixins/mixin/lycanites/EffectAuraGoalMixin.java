package rlmixins.mixin.lycanites;

import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.PotionBase;
import com.lycanitesmobs.core.entity.goals.actions.abilities.EffectAuraGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Locale;

@Mixin(EffectAuraGoal.class)
public abstract class EffectAuraGoalMixin {

    @Redirect(
            method = "setEffect(Ljava/lang/String;)Lcom/lycanitesmobs/core/entity/goals/actions/abilities/EffectAuraGoal;",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/ObjectManager;getEffect(Ljava/lang/String;)Lcom/lycanitesmobs/PotionBase;"),
            remap = false
    )
    public PotionBase rlmixins_lycanitesMobsEffectAuraGoal_setEffect(String name) {
        return ObjectManager.getEffect(name.toLowerCase(Locale.ROOT));
    }
}