package rlmixins.mixin.mobends;

import goblinbob.mobends.core.bender.EntityBender;
import goblinbob.mobends.core.client.event.EntityRenderHandler;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rlmixins.wrapper.IEntityMixin;

@Mixin(EntityRenderHandler.class)
public abstract class EntityRenderHandlerMixin {
	
	@Inject(
			method = "beforeLivingRender",
			at = @At(value = "INVOKE", target = "Lgoblinbob/mobends/core/bender/EntityBender;isAnimated()Z", shift = At.Shift.BEFORE),
			cancellable = true,
			locals = LocalCapture.CAPTURE_FAILHARD,
			remap = false
	)
	public void rlmixins_moBendsEntityRenderHandler_beforeLivingRender(RenderLivingEvent.Pre<? extends EntityLivingBase> event, CallbackInfo ci, EntityLivingBase living, EntityBender entityBender, RenderLivingBase renderer, float pt) {
		if(((IEntityMixin)living).rlmixins$isFakeEntity()) {
			entityBender.deapplyMutation(event.getRenderer(), living);
			ci.cancel();
		}
	}
}