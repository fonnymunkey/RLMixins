package rlmixins.mixin.vanilla;

import net.minecraftforge.common.ForgeHooks;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ForgeHooks.class)
public abstract class ForgeHooksMixin {

    @Redirect(
            method = "lambda$loadAdvancements$0",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V"),
            remap = false
    )
    private static void rlmixins_forgeForgeHooks_loadAdvancements(Logger instance, String s, Throwable throwable) {
        //noop
    }
}