package rlmixins.mixin.betterfoliage;

import mods.betterfoliage.client.config.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.chunkanimator.ChunkAnimatorCooldownHandler;

@Mixin(Config.class)
public abstract class ConfigMixin {

    @Inject(
            method = "onChange",
            at = @At("HEAD"),
            remap = false
    )
    public void rlmixins_betterFoliageConfig_setEnabled(ConfigChangedEvent.PostConfigChangedEvent event, CallbackInfo ci) {
        ChunkAnimatorCooldownHandler.startCooldown();
    }
}
