package rlmixins.mixin.vanilla;

import net.minecraft.advancements.AdvancementManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AdvancementManager.class)
public abstract class AdvancementManagerMixin {

    @Redirect(
            method = "loadCustomAdvancements",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V", remap = false)
    )
    public void rlmixins_vanillaAdvancementManager_loadCustomAdvancements0(Logger instance, String s) {
        //noop
    }

    @Redirect(
            method = "loadCustomAdvancements",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V", remap = false)
    )
    public void rlmixins_vanillaAdvancementManager_loadCustomAdvancements1(Logger instance, String s, Throwable throwable) {
        //noop
    }

    @Redirect(
            method = "loadBuiltInAdvancements",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V", remap = false)
    )
    public void rlmixins_vanillaAdvancementManager_loadBuiltInAdvancements0(Logger instance, String s) {
        //noop
    }

    @Redirect(
            method = "loadBuiltInAdvancements",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V", remap = false)
    )
    public void rlmixins_vanillaAdvancementManager_loadBuiltInAdvancements1(Logger instance, String s, Throwable throwable) {
        //noop
    }
}