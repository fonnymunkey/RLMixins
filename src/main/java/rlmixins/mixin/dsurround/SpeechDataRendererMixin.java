package rlmixins.mixin.dsurround;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.orecruncher.dsurround.client.renderer.SpeechDataRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpeechDataRenderer.class)
public abstract class SpeechDataRendererMixin {

    @Unique
    private static int rlmixins$oldProgram = 0;

    @Inject(
            method = "onRenderWorldLast",
            at = @At(value = "INVOKE", target = "Lorg/orecruncher/dsurround/client/renderer/SpeechDataRenderer;doRender(Lnet/minecraft/entity/Entity;Lorg/orecruncher/dsurround/capabilities/speech/ISpeechData;F)V", shift = At.Shift.BEFORE),
            remap = false
    )
    private static void rlmixins_dSurroundSpeechDataRenderer_onRenderWorldLast_pre(RenderWorldLastEvent event, CallbackInfo ci) {
        rlmixins$oldProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM);
        GL20.glUseProgram(0);
    }

    @Inject(
            method = "onRenderWorldLast",
            at = @At(value = "INVOKE", target = "Lorg/orecruncher/dsurround/client/renderer/SpeechDataRenderer;doRender(Lnet/minecraft/entity/Entity;Lorg/orecruncher/dsurround/capabilities/speech/ISpeechData;F)V", shift = At.Shift.AFTER),
            remap = false
    )
    private static void rlmixins_dSurroundSpeechDataRenderer_onRenderWorldLast_post(RenderWorldLastEvent event, CallbackInfo ci) {
        GL20.glUseProgram(rlmixins$oldProgram);
        rlmixins$oldProgram = 0;
    }
}