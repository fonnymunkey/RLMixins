package rlmixins.mixin.vanilla;

import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vazkii.quark.base.asm.ASMHooks;

@Mixin(LayerElytra.class)
public abstract class LayerElytraMixin {
	
	@Inject(
			method = "doRenderLayer",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/LayerArmorBase;renderEnchantedGlint(Lnet/minecraft/client/renderer/entity/RenderLivingBase;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/model/ModelBase;FFFFFFF)V"),
			locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void rlmixins_vanillaLayerElytra_doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci, ItemStack itemstack) {
		ASMHooks.setColorRuneTargetStack(itemstack);
	}
}