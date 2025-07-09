package rlmixins.mixin.defiledlands;

import lykrast.defiledlands.common.item.ItemSwordScarlite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ConfigHandler;

@Mixin(ItemSwordScarlite.class)
public abstract class ItemSwordScarlite_ConfigMixin {
	
	@Redirect(
			method = "hitEntity",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V")
	)
	private void rlmixins_defiledLandsItemSwordScarlite_hitEntity(EntityLivingBase instance, PotionEffect effect) {
		if(!instance.world.isRemote) {
			instance.addPotionEffect(new PotionEffect(
					ConfigHandler.DEFILEDLANDS_CONFIG.getScarliteReplacementPotion(),
					ConfigHandler.DEFILEDLANDS_CONFIG.scarliteSwordDuration,
					ConfigHandler.DEFILEDLANDS_CONFIG.scarliteSwordAmplifier));
		}
	}
}