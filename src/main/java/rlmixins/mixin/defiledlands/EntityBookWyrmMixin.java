package rlmixins.mixin.defiledlands;

import lykrast.defiledlands.common.entity.passive.EntityBookWyrm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(EntityBookWyrm.class)
public abstract class EntityBookWyrmMixin {

	@Redirect(
			method = "setOffspringAttributes",
			at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"),
			remap = false
	)
	public int rlmixins_defiledLandsEntityBookWyrm_setOffspringAttributes(int i, int j) {
		return Math.min(i, ForgeConfigHandler.server.bookWyrmMaxLevel);
	}
}