package rlmixins.mixin.vanilla;

import net.minecraft.inventory.SlotFurnaceOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SlotFurnaceOutput.class)
public abstract class SlotFurnaceOutputMixin {
	
	@ModifyConstant(
			method = "onCrafting(Lnet/minecraft/item/ItemStack;)V",
			constant = @Constant(floatValue = 1.0F)
	)
	public float rlmixins_vanillaSlotFurnaceOutput_onCrafting(float constant) {
		return Float.MAX_VALUE;
	}
}