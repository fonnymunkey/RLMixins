package rlmixins.mixin.lycanitesmobs;

import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityMixin {

    @Inject(
            method = "canAttackEntity",
            at = @At(value = "RETURN"),
            cancellable = true,
            remap = false
    )
    private void rlmixins_lycanitesMobsBaseCreatureEntity_canAttackEntity(EntityLivingBase targetEntity, CallbackInfoReturnable<Boolean> cir) {
        if(cir.getReturnValue()) {
            if(targetEntity instanceof EntityLiving) {
                if(((EntityLiving)targetEntity).isAIDisabled()) {
                    cir.setReturnValue(false);
                    return;
                }
            }
            IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(targetEntity);
            if((capability != null && capability.isStoned())) {
                cir.setReturnValue(false);
            }
        }
    }
}