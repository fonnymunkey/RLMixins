package rlmixins.mixin.infernalmobs;

import atomicstryker.infernalmobs.client.RendererBossGlow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererBossGlow.class)
public abstract class RendererBossGlowMixin {

    /**
     * Make Infernal Mobs not spam particles while the game is paused
     */
    @Inject(
            method = "onRenderWorldLast",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void rlmixins_infernalMobsRendererBossGlow_onRenderWorldLast(RenderWorldLastEvent event, CallbackInfo ci) {
        if(Minecraft.getMinecraft().isGamePaused()) ci.cancel();
    }
}
