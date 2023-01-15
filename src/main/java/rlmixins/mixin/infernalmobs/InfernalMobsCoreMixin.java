package rlmixins.mixin.infernalmobs;

import atomicstryker.infernalmobs.common.InfernalMobsCore;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.ChampionWrapper;

@Mixin(InfernalMobsCore.class)
public abstract class InfernalMobsCoreMixin {

    /**
     * Prevent Infernals from making Champions an Infernal
     */
    @Inject(
            method = "processEntitySpawn",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_infernalMobsInfernalMobsCore_processEntitySpawn(EntityLivingBase entity, CallbackInfo ci) {
        if(entity instanceof EntityLiving && ChampionWrapper.isEntityChampion((EntityLiving)entity)) ci.cancel();
    }
}