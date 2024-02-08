package rlmixins.mixin.vanilla;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import rlmixins.wrapper.BloodmoonWrapper;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererBloodmoonMixin {

    @Unique
    private int rlmixins$i = 0;

    @Inject(
            method = "updateLightmap",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldProvider;getLightBrightnessTable()[F", ordinal = 0),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void rlmixins_vanillaEntityRendererBloodmoon_updateLightmap_i(float partialTicks, CallbackInfo ci, World world, float f, float f1, int i) {
        rlmixins$i = i;
    }

    @ModifyVariable(
            method = "updateLightmap",
            at = @At(value = "STORE", opcode = 54),
            index = 20
    )
    public int rlmixins_vanillaEntityRendererBloodmoon_updateLightmap_red(int original) {
        return BloodmoonWrapper.manipulateRed(rlmixins$i, original);
    }

    @ModifyVariable(
            method = "updateLightmap",
            at = @At(value = "STORE", opcode = 54),
            index = 21
    )
    public int rlmixins_vanillaEntityRendererBloodmoon_updateLightmap_green(int original) {
        return BloodmoonWrapper.manipulateGreen(rlmixins$i, original);
    }

    @ModifyVariable(
            method = "updateLightmap",
            at = @At(value = "STORE", opcode = 54),
            index = 22
    )
    public int rlmixins_vanillaEntityRendererBloodmoon_updateLightmap_blue(int original) {
        return BloodmoonWrapper.manipulateBlue(rlmixins$i, original);
    }
}