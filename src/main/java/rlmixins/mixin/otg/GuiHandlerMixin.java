package rlmixins.mixin.otg;

import com.pg85.otg.configuration.dimensions.DimensionConfigGui;
import com.pg85.otg.configuration.world.WorldConfig;
import com.pg85.otg.forge.gui.GuiHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiHandler.class)
public abstract class GuiHandlerMixin {

    @Redirect(
            method = "loadGuiPresets",
            at = @At(value = "NEW", target = "(Ljava/lang/String;IZLcom/pg85/otg/configuration/world/WorldConfig;)Lcom/pg85/otg/configuration/dimensions/DimensionConfigGui;"),
            remap = false
    )
    private static DimensionConfigGui rlmixins_otgGuiHandler_loadGuiPresets_1(String presetName, int dimensionId, boolean showInWorldCreationGUI, WorldConfig worldConfig) {
        return new DimensionConfigGui(presetName, dimensionId, presetName.equals("DregoraRL"), worldConfig);
    }
}