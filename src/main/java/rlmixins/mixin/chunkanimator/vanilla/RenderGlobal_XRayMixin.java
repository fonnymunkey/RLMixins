package rlmixins.mixin.chunkanimator.vanilla;

import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.chunkanimator.ChunkAnimationHandler;

@Mixin(RenderGlobal.class)
public abstract class RenderGlobal_XRayMixin {
	
	@Inject(
			method = "loadRenderers",
			at = @At("HEAD")
	)
	private void rlmixins_vanillaRenderGlobal_loadRenderers(CallbackInfo ci) {
		ChunkAnimationHandler.startCooldown();
	}
}