package rlmixins.mixin.vanilla;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

import java.util.Arrays;

@Mixin(EntityTippedArrow.class)
public abstract class EntityTippedArrowMixin {

    @Redirect(
            method = "arrowHit",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V")
    )
    public void rlmixins_vanillEntityTippedArrow_arrowHit(EntityLivingBase instance, PotionEffect effect) {
        if(effect.getPotion().getRegistryName() == null || !Arrays.asList(ForgeConfigHandler.server.tippedArrowBlacklist).contains(effect.getPotion().getRegistryName().toString())) {
            instance.addPotionEffect(effect);
        }
    }
}