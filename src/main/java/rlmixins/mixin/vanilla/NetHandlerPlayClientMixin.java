package rlmixins.mixin.vanilla;

import net.minecraft.client.network.NetHandlerPlayClient;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {
	
	@Redirect(
			method = "handleSetPassengers",
			at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V", remap = false)
	)
	public void rlmixins_vanillaNetHandlerPlayClient_handleSetPassengers(Logger instance, String s) {
		//noop
	}
}