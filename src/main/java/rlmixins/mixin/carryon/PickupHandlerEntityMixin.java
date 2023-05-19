package rlmixins.mixin.carryon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tschipp.carryon.common.handler.PickupHandler;

@Mixin(PickupHandler.class)
public abstract class PickupHandlerEntityMixin {

    @Inject(
            method = "canPlayerPickUpEntity",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_carryOnPickupHandler_canPlayerPickupEntity(EntityPlayer player, Entity toPickUp, CallbackInfoReturnable<Boolean> cir) {
        if(toPickUp instanceof EntityPig && ((EntityPig)toPickUp).getSaddled()) cir.setReturnValue(false);
        if((toPickUp instanceof EntityCow || toPickUp instanceof EntitySquid) && toPickUp.getEntityData().getShort("milk_cooldown") > 0) cir.setReturnValue(false);
        if(toPickUp.isDead || !toPickUp.isEntityAlive()) cir.setReturnValue(false);
    }
}