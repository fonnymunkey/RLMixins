package rlmixins.mixin.firstaid;

import ichttt.mods.firstaid.client.util.HealthRenderUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import rlmixins.handlers.ForgeConfigHandler;

@Mixin(HealthRenderUtils.class)
public abstract class HealthRenderUtilsMixin {
    @ModifyConstant(
            method = "drawAsString",
            constant = @Constant(intValue = 12),
            remap = false
    )
    private static int rlmixins_firstAidHealthRenderUtils_drawAsString(int constant){
        return ForgeConfigHandler.client.firstAidHealthThreshold;
    }
}