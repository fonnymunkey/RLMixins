package rlmixins.mixin.enhancedvisuals;

import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import team.creative.enhancedvisuals.client.render.EVRenderer;
import team.creative.enhancedvisuals.common.death.DeathMessages;

@Mixin(EVRenderer.class)
public abstract class EVRendererMixin {

    @Redirect(
            method = "render",
            at = @At(value = "INVOKE", target = "Lteam/creative/enhancedvisuals/common/death/DeathMessages;pickRandomDeathMessage()Ljava/lang/String;"),
            remap = false
    )
    private static String rlmixins_enhancedVisualsEVRenderer_render(DeathMessages instance) {
        return I18n.format(instance.pickRandomDeathMessage());
    }
}