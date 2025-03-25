package rlmixins.mixin.quark;

import net.minecraft.client.model.ModelRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.arl.item.ModelModArmor;
import vazkii.quark.vanity.client.model.ModelWitchHat;

@Mixin(ModelWitchHat.class)
public abstract class ModelWitchHatMixin extends ModelModArmor {

    /*
    *   Moving setters to constructor seems to solve most rendering issues
     */

    @Shadow(remap = false)
    @Final
    private ModelRenderer witchHat;

    @Inject(
            method = "<init>",
            at = @At("RETURN"),
            remap = true
    )
    private void rlmixins_quarkModelWitchHat_init(CallbackInfo ci){
        this.bipedHead.showModel = false;
        this.bipedHeadwear = this.witchHat;
        this.bipedHead = this.witchHat;
    }

    @Inject(
            method = "setModelParts",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void rlmixins_quarkModelWitchHat_setModelParts(CallbackInfo ci){
        ci.cancel();
    }
}
