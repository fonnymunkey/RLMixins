package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Utility_Sector.OtherHandler;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OtherHandler.class)
public abstract class OtherHandlerMixin {

    @Inject(
            method = "onPlayerCapAttach",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_soManyEnchantmentsOtherHandler_onPlayerCapAttach(AttachCapabilitiesEvent<Entity> event, CallbackInfo ci) {
        ci.cancel();
    }
}