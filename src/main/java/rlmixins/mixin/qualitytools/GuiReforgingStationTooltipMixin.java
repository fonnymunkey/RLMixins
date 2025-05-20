package rlmixins.mixin.qualitytools;

import com.tmtravlr.qualitytools.reforging.GuiButtonReforgingStation;
import com.tmtravlr.qualitytools.reforging.GuiReforgingStation;
import com.tmtravlr.qualitytools.reforging.TileEntityReforgingStation;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiReforgingStation.class)
public abstract class GuiReforgingStationTooltipMixin extends GuiContainer {

    @Shadow(remap = false)
    @Final
    private TileEntityReforgingStation tileReforgingStation;
    @Shadow(remap = false)
    private GuiButtonReforgingStation reforgeButton;

    public GuiReforgingStationTooltipMixin(Container p_i1072_1_) {
        super(p_i1072_1_);
    }

    @Inject(
            method = "drawScreen",
            at = @At("TAIL")
    )
    public void rlmixins_qualityToolsGuiReforgingStation_drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        ItemStack tool = this.tileReforgingStation.getStackInSlot(0);
        // tfw someone made isMouseOver useless
        boolean hoveringReforge = mouseX >= reforgeButton.x && mouseY >= reforgeButton.y && mouseX < reforgeButton.x + reforgeButton.width && mouseY < reforgeButton.y + reforgeButton.height;
        if (this.mc.player.inventory.getItemStack().isEmpty() && hoveringReforge && tool != null){
            this.renderToolTip(tool, mouseX, mouseY + 20);
        }
    }
}
