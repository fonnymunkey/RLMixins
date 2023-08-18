package rlmixins.mixin.quark;

import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.tweaks.feature.DoubleDoors;

@Mixin(DoubleDoors.class)
public abstract class DoubleDoorsMixin {

    @Inject(
            method = "onEntityTick",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_quarkDoubleDoors_onEntityTick(LivingEvent.LivingUpdateEvent event, CallbackInfo ci) {
        if(event.getEntity().ticksExisted%20!=0) ci.cancel();
    }
}