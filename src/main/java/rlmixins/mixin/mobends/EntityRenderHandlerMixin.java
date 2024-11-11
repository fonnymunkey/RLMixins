package rlmixins.mixin.mobends;

import goblinbob.mobends.core.client.event.EntityRenderHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.wrapper.IEntityMixin;

@Mixin(EntityRenderHandler.class)
public abstract class EntityRenderHandlerMixin {
	
	@Inject(
			method = "beforeLivingRender",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	public void rlmixins_moBendsEntityRenderHandler_beforeLivingRender(RenderLivingEvent.Pre<? extends EntityLivingBase> event, CallbackInfo ci) {
		if(event.getEntity() != null && ((IEntityMixin)event.getEntity()).rlmixins$isFakeEntity()) ci.cancel();
	}
	
	@Inject(
			method = "afterLivingRender",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	public void rlmixins_moBendsEntityRenderHandler_afterLivingRender(RenderLivingEvent.Post<? extends EntityLivingBase> event, CallbackInfo ci) {
		if(event.getEntity() != null && ((IEntityMixin)event.getEntity()).rlmixins$isFakeEntity()) ci.cancel();
	}
}