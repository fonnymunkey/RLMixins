package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeCleaver;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(WeaponMeleeCleaver.class)
public abstract class WeaponMeleeCleaverMixin {

    @Redirect(
            method = "func_77644_a",
            at = @At(value = "INVOKE", target = "Lcom/dhanantry/scapeandrunparasites/init/SRPPotions;applyStackPotion(Lnet/minecraft/potion/Potion;Lnet/minecraft/entity/EntityLivingBase;II)V"),
            remap = false
    )
    public void rlmixins_srParasitesWeaponMeleeCleaver_onHit(Potion potion, EntityLivingBase entity, int duration, int amp) {
        entity.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation(ForgeConfigHandler.server.parasiteCleaverEffect), duration, amp == 0 ? ForgeConfigHandler.server.livingCleaverAmplifier : ForgeConfigHandler.server.sentientCleaverAmplifier, false, false));
    }
}