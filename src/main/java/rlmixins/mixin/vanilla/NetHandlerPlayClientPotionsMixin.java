package rlmixins.mixin.vanilla;

import rlmixins.wrapper.IClientPotionApplier;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.SPacketEntityEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientPotionsMixin {
	@Shadow private WorldClient world;

	@Inject(
			method = "handleEntityEffect",
			at = @At("HEAD")
	)
	void rlmixins_netHandlerPlayClient_handleEntityEffect(SPacketEntityEffect packetIn, CallbackInfo ci){
		Entity entity = this.world.getEntityByID(packetIn.getEntityId());
		if(entity == null) return;
		if(entity instanceof EntityLivingBase)
			((IClientPotionApplier) entity).rLMixins$setIsPacket(true);
	}
}