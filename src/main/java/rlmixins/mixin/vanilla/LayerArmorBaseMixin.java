package rlmixins.mixin.vanilla;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.quark.base.asm.ASMHooks;

@Mixin(LayerArmorBase.class)
public abstract class LayerArmorBaseMixin {

    @Redirect(
            method = "renderEnchantedGlint",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V", ordinal = 1)
    )
    private static void rlmixins_vanillaLayerArmorBase_renderEnchantedGlint(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
        GlStateManager.color(colorRed, colorGreen, colorBlue, colorAlpha);
        ASMHooks.applyRuneColor();
    }
}