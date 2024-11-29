package rlmixins.mixin.otg;

import com.pg85.otg.common.LocalMaterialData;
import com.pg85.otg.configuration.biome.settings.ReplacedBlocksMatrix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ReplacedBlocksMatrix.class)
public abstract class ReplacedBlocksMatrixMixin {

    /**
     * Fixes an ArrayIndexOutOfBoundsException when a Y-level of -1 is provided to the replaceBlock function.
     */
    @Inject(
            method = "replaceBlock",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_otgReplacedBlocksMatrix_replaceBlock(int y, LocalMaterialData material, CallbackInfoReturnable<LocalMaterialData> cir) {
        if (y < 0) {
            cir.setReturnValue(material);
        }
    }
}
