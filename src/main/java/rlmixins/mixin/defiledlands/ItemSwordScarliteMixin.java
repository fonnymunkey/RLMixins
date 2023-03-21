package rlmixins.mixin.defiledlands;

import lykrast.defiledlands.common.item.ItemSwordScarlite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(ItemSwordScarlite.class)
public abstract class ItemSwordScarliteMixin {

    @Redirect(
            method = "hitEntity",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V")
    )
    public void rlmixins_defiledLandsItemSwordScarlite_hitEntity(EntityLivingBase instance, PotionEffect effect) {
        instance.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(ForgeConfigHandler.server.scarliteSwordEffect), ForgeConfigHandler.server.scarliteSwordDuration, ForgeConfigHandler.server.scarliteSwordAmplifier));
    }
}