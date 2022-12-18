package rlmixins.mixin.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    private float lastEyeHeight;
    private float eyeHeight;
    private float partialTicks;

    /**
     * Smooth eye height when crouching, based on RandomPatches's method
     * https://github.com/TheRandomLabs/RandomPatches/blob/1.12/src/main/java/com/therandomlabs/randompatches/hook/client/EntityRendererHook.java
     */
    @Inject(
            method = "updateRenderer",
            at= @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/WorldClient;getLightBrightness(Lnet/minecraft/util/math/BlockPos;)F")
    )
    public void rlmixins_vanillaEntityRenderer_updateRenderer(CallbackInfo ci) {
        if(this.eyeHeight == 0) this.eyeHeight = Minecraft.getMinecraft().getRenderViewEntity().getEyeHeight();
        this.lastEyeHeight = this.eyeHeight;
        this.eyeHeight += (Minecraft.getMinecraft().getRenderViewEntity().getEyeHeight() - this.eyeHeight)*0.5F;
    }

    @Inject(
            method = "orientCamera",
            at = @At("HEAD")
    )
    public void rlmixins_vanillaEntityRenderer_orientCameraHead(float partialTicks, CallbackInfo ci) {
        this.partialTicks = partialTicks;
    }

    @Redirect(
            method = "orientCamera",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F")
    )
    public float rlmixins_vanillaEntityRenderer_orientCamera(Entity instance) {
        return (this.lastEyeHeight + (this.eyeHeight-this.lastEyeHeight)*this.partialTicks);
    }
}