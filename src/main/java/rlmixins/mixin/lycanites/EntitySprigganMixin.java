package rlmixins.mixin.lycanites;

import com.lycanitesmobs.core.entity.creature.EntitySpriggan;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(EntitySpriggan.class)
public abstract class EntitySprigganMixin {

    @Shadow(remap = false) public int farmingRate;

    @Inject(
            method = "onLivingUpdate",
            at = @At("HEAD")
    )
    public void rlmixins_lycanitesEntitySpriggan_onLivingUpdate(CallbackInfo ci) {
        this.farmingRate = ForgeConfigHandler.server.sprigganFarmingRate;
    }
}