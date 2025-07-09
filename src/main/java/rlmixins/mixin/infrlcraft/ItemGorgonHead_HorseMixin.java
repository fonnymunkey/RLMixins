package rlmixins.mixin.infrlcraft;

import com.github.alexthe666.iceandfire.item.ItemGorgonHead;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.CallableHorsesWrapper;

/**
 * Fix by cdstk
 */
@Mixin(ItemGorgonHead.class)
public abstract class ItemGorgonHead_HorseMixin {
	
	@Inject(
			method = "onPlayerStoppedUsing",
			at = @At(value = "INVOKE", target = "Lcom/github/alexthe666/iceandfire/api/IEntityEffectCapability;setStoned()V", remap = false)
	)
	private void rlmixins_infRLCraftItemGorgonHead_onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entity, int timeLeft, CallbackInfo ci, @Local(ordinal = 0) Entity pointedEntity) {
		CallableHorsesWrapper.applyCallableHorseDeath(pointedEntity);
	}
}