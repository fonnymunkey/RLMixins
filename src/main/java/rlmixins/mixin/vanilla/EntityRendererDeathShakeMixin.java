package rlmixins.mixin.vanilla;

import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererDeathShakeMixin {

    @ModifyVariable(
            method = "hurtCameraEffect",
            at = @At(value = "STORE"),
            index = 4
    )
    public float rlmixins_vanillaEntityRenderer_hurtCameraEffect(float value) {
        return Math.min(20, value);
    }
}