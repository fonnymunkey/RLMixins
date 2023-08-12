package rlmixins.mixin.xat;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xzeroair.trinkets.events.EventHandler;

@Mixin(EventHandler.class)
public abstract class EventHandlerMixin {

    @Redirect(
            method = "EntityUpdate",
            at = @At(value = "INVOKE", target = "Lxzeroair/trinkets/events/EventHandler;magicHandlerTick(Lnet/minecraft/entity/EntityLivingBase;)V"),
            remap = false
    )
    public void rlmixins_xatEventHandler_EntityUpdate(EventHandler instance, EntityLivingBase entity) {
        //noop
    }
}