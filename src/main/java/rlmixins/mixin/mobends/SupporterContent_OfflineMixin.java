package rlmixins.mixin.mobends;

import goblinbob.mobends.core.connection.PlayerSettingsResponse;
import goblinbob.mobends.core.supporters.AccessoryDetails;
import goblinbob.mobends.core.supporters.AccessorySettings;
import goblinbob.mobends.core.supporters.SupporterContent;
import goblinbob.mobends.core.util.Color;
import goblinbob.mobends.core.util.IColorRead;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Mixin(SupporterContent.class)
public abstract class SupporterContent_OfflineMixin {

    @Shadow(remap = false)
    @Final
    private static Color DEFAULT_TRAIL_COLOR;

    @Inject(
            method = "fetchAccessoryDetails",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_moBendsSupporterContent_fetch(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "registerPlayerAccessorySettings",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_moBendsSupporterContent_registerPlayerAccessorySettings(String playerName, PlayerSettingsResponse settings, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(
            method = "getAccessories",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_moBendsSupporterContent_getAccessories(CallbackInfoReturnable<Set<Map.Entry<String, AccessoryDetails>>> cir) {
        cir.setReturnValue(Collections.emptySet());
    }

    @Inject(
            method = "getAccessorySettingsMapFor",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_moBendsSupporterContent_getAccessorySettingsMapFor(EntityLivingBase entity, CallbackInfoReturnable<Map<String, AccessorySettings>> cir) {
        cir.setReturnValue(Collections.emptyMap());
    }

    @Inject(
            method = "getTrailColorFor",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void rlmixins_moBendsSupporterContent_getTrailColorFor(EntityLivingBase entity, CallbackInfoReturnable<IColorRead> cir) {
        cir.setReturnValue(DEFAULT_TRAIL_COLOR);
    }
}