package rlmixins.mixin.vanilla;

import net.minecraft.client.gui.GuiRepair;
import net.minecraft.entity.player.PlayerCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiRepair.class)
public abstract class GuiRepairMixin {

    /**
     * Make the Anvil GUI skip the "Too Expensive" check since Anvil Patch fixes it serverside
     */
    @Redirect(
            method = "drawGuiContainerForegroundLayer",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerCapabilities;isCreativeMode:Z")
    )
    public boolean rlmixins_vanillaGuiRepair_drawGuiContainerForegroundLayer(PlayerCapabilities instance) {
        return true;
    }
}