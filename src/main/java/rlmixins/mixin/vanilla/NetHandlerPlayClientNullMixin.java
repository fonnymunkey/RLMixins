package rlmixins.mixin.vanilla;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientNullMixin {
	
	@Redirect(
			method = "handleEntityStatus",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/server/SPacketEntityStatus;getEntity(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;")
	)
	public Entity rlmixins_vanillaNetHandlerPlayClient_handleEntityStatus(SPacketEntityStatus instance, World worldIn) {
		return worldIn == null ? null : instance.getEntity(worldIn);
	}
	
	@Redirect(
			method = "handleEntityHeadLook",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/server/SPacketEntityHeadLook;getEntity(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;")
	)
	public Entity rlmixins_vanillaNetHandlerPlayClient_handleEntityHeadLook(SPacketEntityHeadLook instance, World worldIn) {
		return worldIn == null ? null : instance.getEntity(worldIn);
	}
	
	@Redirect(
			method = "handleEntityMovement",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/server/SPacketEntity;getEntity(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;")
	)
	public Entity rlmixins_vanillaNetHandlerPlayClient_handleEntityMovement(SPacketEntity instance, World worldIn) {
		return worldIn == null ? null : instance.getEntity(worldIn);
	}
}