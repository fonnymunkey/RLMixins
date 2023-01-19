package rlmixins.mixin.champions;

import c4.champions.common.affix.AffixEvents;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AffixEvents.class)
public abstract class AffixEventsMixin {

    @Redirect(
            method = "onLivingJoinWorld",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLiving;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V")
    )
    public void rlmixins_championsAffixEvents_onLivingJoinWorld(EntityLiving instance, PotionEffect potionEffect) {
        instance.addPotionEffect(new PotionEffect(potionEffect.getPotion(), potionEffect.getDuration(), potionEffect.getAmplifier(), false, false));
    }

    @Redirect(
            method = "onLivingUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLiving;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V")
    )
    public void rlmixins_championsAffixEvents_onLivingUpdate(EntityLiving instance, PotionEffect potionEffect) {
        instance.addPotionEffect(new PotionEffect(potionEffect.getPotion(), potionEffect.getDuration(), potionEffect.getAmplifier(), false, false));
    }
}