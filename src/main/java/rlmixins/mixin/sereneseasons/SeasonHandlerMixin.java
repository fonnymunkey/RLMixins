/*
package rlmixins.mixin.sereneseasons;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rlmixins.handlers.chunkanimator.ChunkAnimatorCooldownHandler;
import sereneseasons.api.season.Season;
import sereneseasons.handler.season.SeasonHandler;

@Mixin(SeasonHandler.class)
public abstract class SeasonHandlerMixin {
    @Shadow(remap = false) private Season.SubSeason lastSeason;

    @Inject(
            method = "onClientTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;loadRenderers()V", shift = At.Shift.BEFORE)
    )
    public void rlmixins_sereneSeasonsSeasonHandler_onClientTick(TickEvent.ClientTickEvent event, CallbackInfo ci) {
        if(lastSeason != null) ChunkAnimatorCooldownHandler.startCooldown();
    }
}

 */