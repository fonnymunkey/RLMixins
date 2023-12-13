package rlmixins.mixin.lycanites;

import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityMixin {

    /**
     * Fix Lycanite mobs targetting and getting stuck targetting statues, or mobs with NoAI tag
     */
    @Inject(
            method = "canAttackEntity",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_lycanitesBaseCreatureEntity_canAttackEntity(EntityLivingBase targetEntity, CallbackInfoReturnable<Boolean> cir) {
        IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(targetEntity);
        if((capability != null && capability.isStoned()) || targetEntity.getEntityData().getBoolean("NoAI")) cir.setReturnValue(false);
    }
}