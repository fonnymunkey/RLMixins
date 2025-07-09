package rlmixins.mixin.mobends;

import goblinbob.mobends.core.pack.PublicDatabase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PublicDatabase.class)
public abstract class PublicDatabase_OfflineMixin {

    @Inject(
            method = "downloadPublicDatabase",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_moBendsPublicDatabase_downloadPublicDatabase(String databaseUrl, CallbackInfoReturnable<PublicDatabase> cir) {
        cir.setReturnValue(null);
    }
}