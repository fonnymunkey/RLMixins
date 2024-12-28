package rlmixins.mixin.clumps;

import com.blamejared.clumps.client.render.RenderXPOrbBig;
import com.blamejared.clumps.entities.EntityXPOrbBig;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.RLMixins;

@Mixin(RenderXPOrbBig.class)
public abstract class RenderXPOrbBigMixin {
	@Unique private static int rlmixins$xpColor;
	@Unique private static float rlmixins$partialTicks;

	@Inject(
			method = "doRender(Lcom/blamejared/clumps/entities/EntityXPOrbBig;DDDFF)V",
			at = @At(value = "HEAD"),
			remap = false
	)
	public void rlmixins_doRender_head(EntityXPOrbBig entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci){
		RenderXPOrbBigMixin.rlmixins$partialTicks = partialTicks;
		RenderXPOrbBigMixin.rlmixins$xpColor = entity.xpColor;
	}

	@ModifyConstant(
			method = "doRender(Lcom/blamejared/clumps/entities/EntityXPOrbBig;DDDFF)V",
			constant = @Constant(intValue = 16777215),
			remap = false
	)
	public int rlmixins_doRender_doRender(int constant){
		float f9 = ((float) rlmixins$xpColor + rlmixins$partialTicks) / 2.0F;
		return (int)((MathHelper.sin(f9 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
	}
}