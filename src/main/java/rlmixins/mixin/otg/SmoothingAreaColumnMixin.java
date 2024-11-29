package rlmixins.mixin.otg;

import com.pg85.otg.common.LocalMaterialData;
import com.pg85.otg.customobjects.structures.bo4.smoothing.SmoothingAreaColumn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SmoothingAreaColumn.class)
public abstract class SmoothingAreaColumnMixin {

    /**
     * Fixes a NPE that occurs during OTG generation.
     */
    @Redirect(
            method = "spawn",
            at = @At(value = "INVOKE", target = "Lcom/pg85/otg/common/LocalMaterialData;isAir()Z"),
            remap = false
    )
    public boolean rlmixins_otgSmoothingAreaColumn_spawn(LocalMaterialData instance) {
        if (instance == null) {
            return false;
        }
        return instance.isAir();
    }
}
