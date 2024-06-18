package rlmixins.mixin.dshud;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.orecruncher.dshuds.Environment;
import org.orecruncher.dshuds.hud.CompassHUD;
import org.orecruncher.lib.PlayerUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Locale;

@Mixin(CompassHUD.class)
public abstract class CompassHUDMixin {

    @Unique
    private static Item rlmixins$BAROMETER;

    @Inject(
            method = "doTick",
            at = @At(value = "INVOKE", target = "Lorg/orecruncher/dshuds/hud/CompassHUD;showCompass()Z", shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false
    )
    public void rlmixins_dsHudCompassHUD_doTick(int tickRef, CallbackInfo ci, List<String> text) {
        if(rlmixins$BAROMETER == null) {
            rlmixins$BAROMETER = ForgeRegistries.ITEMS.getValue(new ResourceLocation("inspirations:barometer"));
            if(rlmixins$BAROMETER == null) rlmixins$BAROMETER = Items.AIR;
        }
        if(rlmixins$BAROMETER == Items.AIR) return;
        if(PlayerUtils.isHolding(Environment.getPlayer(), rlmixins$BAROMETER)) {
            BlockPos pos = Environment.getPlayerPosition();
            text.add(TextFormatting.AQUA + String.format(Locale.getDefault(), "y: %1$d", pos.getY()));
        }
    }
}