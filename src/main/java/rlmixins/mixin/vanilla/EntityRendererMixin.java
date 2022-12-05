package rlmixins.mixin.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    private float lastEyeHeight;
    private float eyeHeight;

    /**
     * Smooth eye height when crouching, based on RandomPatches's method
     * https://github.com/TheRandomLabs/RandomPatches/blob/1.12/src/main/java/com/therandomlabs/randompatches/hook/client/EntityRendererHook.java
     */
    @Inject(
            method = "updateRenderer",
            at= @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;getLightBrightness(Lnet/minecraft/util/math/BlockPos;)F")
    )
    public void rlmixins_vanillaEntityRenderer_updateRenderer(CallbackInfo ci) {
        this.lastEyeHeight = this.eyeHeight;
        this.eyeHeight += (Minecraft.getMinecraft().getRenderViewEntity().getEyeHeight() - this.eyeHeight)*0.5F;
    }

    /**
     * Smooth eye height when crouching, based on RandomPatches's method
     * https://github.com/TheRandomLabs/RandomPatches/blob/1.12/src/main/java/com/therandomlabs/randompatches/hook/client/EntityRendererHook.java
     */
    @Inject(
            method = "setupCameraTransform",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;orientCamera(F)V", shift = At.Shift.AFTER)
    )
    public void rlmixins_vanillaEntityRenderer_setupCameraTransform(float partialTicks, int pass, CallbackInfo ci) {
        GlStateManager.translate(0.0F, Minecraft.getMinecraft().getRenderViewEntity().getEyeHeight() - (this.lastEyeHeight + (this.eyeHeight-this.lastEyeHeight)*partialTicks), 0.0F);
    }
}