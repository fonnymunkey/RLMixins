package rlmixins.mixin.srparasites;

import com.dhanantry.scapeandrunparasites.item.tool.WeaponMeleeCleaver;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ConfigHandler;

@Mixin(WeaponMeleeCleaver.class)
public abstract class WeaponMeleeCleaver_EffectMixin {
	
	@Redirect(
			method = "hitEntity",
			at = @At(value = "INVOKE", target = "Lcom/dhanantry/scapeandrunparasites/init/SRPPotions;applyStackPotion(Lnet/minecraft/potion/Potion;Lnet/minecraft/entity/EntityLivingBase;II)V", remap = false)
	)
	private void rlmixins_srparasitesWeaponMeleeCleaver_hitEntity(Potion potion, EntityLivingBase entity, int dur, int amp) {
		if(!entity.world.isRemote) {
			entity.addPotionEffect(new PotionEffect(
					ConfigHandler.SRPARASITES_CONFIG.getCleaverEffect(),
					dur,
					amp == 0 ? ConfigHandler.SRPARASITES_CONFIG.livingCleaverAmplifier : ConfigHandler.SRPARASITES_CONFIG.sentientCleaverAmplifier,
					false,
					false));
		}
	}
}