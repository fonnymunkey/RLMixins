package rlmixins.mixin.jei;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "mezz.jei.plugins.vanilla.crafting.CraftingRecipeChecker$CraftingRecipeValidator")
public abstract class CraftingRecipeChecker_VCMixin {
	
	@Redirect(
			method = "isRecipeValid",
			at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V"),
			remap = false
	)
	private void rlmixins_jeiCraftingRecipeChecker_isRecipeValid(Logger instance, String s, Object o) {
		if(!((String)o).startsWith("Varied Commodities")) instance.error(s, o);
	}
}