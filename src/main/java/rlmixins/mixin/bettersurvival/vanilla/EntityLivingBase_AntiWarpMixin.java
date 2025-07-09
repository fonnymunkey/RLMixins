package rlmixins.mixin.bettersurvival.vanilla;

import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlmixins.util.ModLoadedUtil;
import rlmixins.wrapper.BetterSurvivalWrapper;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBase_AntiWarpMixin {
	
	@Inject(
			method = "attemptTeleport",
			at = @At("HEAD"),
			cancellable = true
	)
	private void rlmixins_vanillaEntityLivingBase_attemptTeleport(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
		if(ModLoadedUtil.isBetterSurvivalLoaded()) {
			if(((EntityLivingBase)(Object)this).getActivePotionEffect(BetterSurvivalWrapper.getAntiWarpPotion()) != null) cir.setReturnValue(false);
		}
	}
}