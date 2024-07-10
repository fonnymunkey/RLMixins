package rlmixins.mixin.otg;

import com.pg85.otg.configuration.dimensions.DimensionConfig;
import com.pg85.otg.forge.gui.dimensions.OTGGuiDimensionList;
import com.pg85.otg.forge.gui.dimensions.base.OTGGuiSlotDimensionList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(OTGGuiDimensionList.class)
public abstract class OTGGuiDimensionListMixin extends GuiScreen {

    @Shadow(remap = false)
    public int leftMargin;

    @Shadow(remap = false)
    private int rightMargin;

    @Redirect(
            method = "initGui",
            at = @At(value = "NEW", target = "(Lcom/pg85/otg/forge/gui/dimensions/OTGGuiDimensionList;Lcom/pg85/otg/forge/gui/dimensions/OTGGuiDimensionList;Ljava/util/ArrayList;)Lcom/pg85/otg/forge/gui/dimensions/base/OTGGuiSlotDimensionList;", remap = false)
    )
    public OTGGuiSlotDimensionList rlmixins_otgOTGGuiDimensionList_initGui_new(OTGGuiDimensionList otgGuiDimensionList, OTGGuiDimensionList parent, ArrayList<DimensionConfig> dimensions) {
        ArrayList<DimensionConfig> newList = new ArrayList<>();
        for(DimensionConfig config : dimensions) {
            if(config.PresetName.equals("DregoraRL")) newList.add(config);
        }
        return new OTGGuiSlotDimensionList(otgGuiDimensionList, parent, newList);
    }

    @Redirect(
            method = "initGui",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0)
    )
    public boolean rlmixins_otgOTGGuiDimensionList_initGui_0(List<GuiButton> instance, Object button) {
        return false;
    }

    @Redirect(
            method = "initGui",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1)
    )
    public boolean rlmixins_otgOTGGuiDimensionList_initGui_1(List<GuiButton> instance, Object button) {
        return false;
    }

    @Redirect(
            method = "initGui",
            at = @At(value = "NEW", target = "(IIIIILjava/lang/String;)Lnet/minecraft/client/gui/GuiButton;", ordinal = 2)
    )
    public GuiButton rlmixins_otgOTGGuiDimensionList_initGui_2(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        int maxBtnWidth = 330;
        int btnWidth = this.width - this.leftMargin - this.rightMargin;
        btnWidth = Math.min(btnWidth, maxBtnWidth);
        return new GuiButton(buttonId, (this.width - btnWidth)/2, y, btnWidth, heightIn, buttonText);
    }

    @Redirect(
            method = "initGui",
            at = @At(value = "NEW", target = "(IIIIILjava/lang/String;)Lnet/minecraft/client/gui/GuiButton;", ordinal = 3)
    )
    public GuiButton rlmixins_otgOTGGuiDimensionList_initGui_3(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        int maxBtnWidth = 330;
        int btnWidth = this.width - this.leftMargin - this.rightMargin;
        btnWidth = Math.min(btnWidth, maxBtnWidth);
        return new GuiButton(buttonId, (this.width - btnWidth)/2, y, btnWidth, heightIn, buttonText);
    }
}