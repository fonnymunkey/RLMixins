package rlmixins.mixin.infrlcraft;

import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.CallableHorsesWrapper;

/**
 * Fix by cdstk
 */
@Mixin(EntityGorgon.class)
public abstract class EntityGorgon_HorseMixin {
	
	@Inject(
			method = "onLivingUpdate",
			at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/api/IEntityEffectCapability;setStoned()V", remap = false)
	)
	private void rlmixins_infRLCraftEntityGorgon_onLivingUpdate(CallbackInfo ci, @Local EntityLiving target) {
		CallableHorsesWrapper.applyCallableHorseDeath(target);
	}
}