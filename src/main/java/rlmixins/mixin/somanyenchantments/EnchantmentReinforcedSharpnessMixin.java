package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_0.EnchantmentReinforcedSharpness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnchantmentReinforcedSharpness.class)
public abstract class EnchantmentReinforcedSharpnessMixin {
	
	@Inject(
			method = "onEntityDamagedAlt",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	public void rlmixins_soManyEnchantmentsEnchantmentReinforcedSharpness_onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack stack, int level, CallbackInfo ci) {
		ci.cancel();
	}
}