package rlmixins.mixin.somanyenchantments;

import com.Shultrea.Rin.Ench0_4_5.EnchantmentEvasion;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentEvasion.class)
public abstract class EnchantmentEvasionMixin {
	
	@Redirect(
			method = "HandleEnchant",
			at = @At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;hurtResistantTime:I")
	)
	public void rlmixins_soManyEnchantmentsEnchantmentEvasion_handleEnchant(EntityLivingBase instance, int value) {
		instance.hurtResistantTime = instance.maxHurtResistantTime + (value - 20);
	}
}