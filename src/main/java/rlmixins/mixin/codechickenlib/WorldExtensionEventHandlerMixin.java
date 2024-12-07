package rlmixins.mixin.codechickenlib;

import codechicken.lib.world.WorldExtensionManager;
import net.minecraftforge.event.world.ChunkWatchEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldExtensionManager.WorldExtensionEventHandler.class)
public abstract class WorldExtensionEventHandlerMixin {
	
	@Inject(
			method = "onChunkUnWatch",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	public void rlmixins_codeChickenLibWorldExtensionEventHandler_onChunkUnWatch(ChunkWatchEvent.UnWatch event, CallbackInfo ci) {
		ci.cancel();
	}
}