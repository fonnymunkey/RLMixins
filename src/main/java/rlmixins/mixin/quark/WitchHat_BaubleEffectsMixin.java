package rlmixins.mixin.quark;

import baubles.api.BaublesApi;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.quark.base.module.Feature;
import vazkii.quark.vanity.feature.WitchHat;

/**
 * By cdstk
 */
@Mixin(WitchHat.class)
public abstract class WitchHat_BaubleEffectsMixin extends Feature {

    @Shadow(remap = false)
    public static Item witch_hat;

    @Inject(
            method = "hasWitchHat",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;getItemStackFromSlot(Lnet/minecraft/inventory/EntityEquipmentSlot;)Lnet/minecraft/item/ItemStack;"),
            cancellable = true
    )
    private static void rlmixins_quarkWitchHat_hasWitchHat(EntityLiving attacker, EntityLivingBase target, CallbackInfoReturnable<Boolean> cir) {
        if(target instanceof EntityPlayer) {
            if(BaublesApi.isBaubleEquipped((EntityPlayer)target, witch_hat) != -1) cir.setReturnValue(true);
        }
    }
}