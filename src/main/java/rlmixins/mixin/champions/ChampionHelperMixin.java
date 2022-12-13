package rlmixins.mixin.champions;

import c4.champions.common.util.ChampionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.wrapper.BlightInfernalWrapper;

@Mixin(ChampionHelper.class)
public abstract class ChampionHelperMixin {

    /**
     * Make Infernals not be able to be Champions as well
     */
    @Inject(
            method = "isValidChampion",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_championsChampionHelper_isValidChampion(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(cir.getReturnValue() && entity instanceof EntityLivingBase && BlightInfernalWrapper.isEntityInfernal((EntityLivingBase)entity)) cir.setReturnValue(false);
    }
}
