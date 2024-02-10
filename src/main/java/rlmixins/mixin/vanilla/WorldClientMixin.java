package rlmixins.mixin.vanilla;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public abstract class WorldClientMixin {

    @Shadow @Final private Minecraft mc;

    @Unique
    private int rlmixins$prevJ = Integer.MAX_VALUE;
    @Unique
    private int rlmixins$prevK = Integer.MAX_VALUE;

    @Inject(
            method = "refreshVisibleChunks",
            at = @At("HEAD"),
            cancellable = true
    )
    public void rlmixins_vanillaWorldClient_refreshVisibleChunks(CallbackInfo ci) {
        int j = MathHelper.floor(this.mc.player.posX / 16.0D);
        int k = MathHelper.floor(this.mc.player.posZ / 16.0D);
        if(rlmixins$prevJ == j && rlmixins$prevK == k) ci.cancel();
        else {
            rlmixins$prevJ = j;
            rlmixins$prevK = k;
        }
    }
}