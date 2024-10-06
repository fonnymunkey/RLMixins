package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.init.SRPItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SRPItems.class)
public abstract class SRPItemsStrangeBoneMixin {

    @Inject(
            method="init",
            at=@At(value="TAIL"),
            remap=false
    )
    private static void increaseStacksizeMixin(CallbackInfo ci){
        SRPItems.bone.setMaxStackSize(16);
    }
}