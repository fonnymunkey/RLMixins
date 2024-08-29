package rlmixins.mixin.otg;

import com.pg85.otg.forge.gui.presets.OTGGuiPresetList;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(OTGGuiPresetList.class)
public abstract class OTGGuiPresetListMixin extends GuiScreen {

    @Shadow(remap = false)
    int leftMargin;

    @Shadow(remap = false)
    int rightMargin;

    @Redirect(
            method = "initGui",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0)
    )
    public boolean rlmixins_otgOTGGuiPresetList_initGui_0(List<GuiButton> instance, Object button) {
        return false;
    }

    @Redirect(
            method = "initGui",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1)
    )
    public boolean rlmixins_otgOTGGuiPresetList_initGui_1(List<GuiButton> instance, Object button) {
        return false;
    }

    @Redirect(
            method = "initGui",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2)
    )
    public boolean rlmixins_otgOTGGuiPresetList_initGui_2(List<GuiButton> instance, Object button) {
        return false;
    }

    @Redirect(
            method = "initGui",
            at = @At(value = "NEW", target = "(IIIIILjava/lang/String;)Lnet/minecraft/client/gui/GuiButton;", ordinal = 3)
    )
    public GuiButton rlmixins_otgOTGGuiPresetList_initGui_3(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        int maxBtnWidth = 330;
        int btnWidth = this.width - this.leftMargin - this.rightMargin;
        btnWidth = Math.min(btnWidth, maxBtnWidth);
        return new GuiButton(buttonId, (this.width - btnWidth)/2, y, btnWidth, heightIn, buttonText);
    }

    @Redirect(
            method = "initGui",
            at = @At(value = "NEW", target = "(IIIIILjava/lang/String;)Lnet/minecraft/client/gui/GuiButton;", ordinal = 4)
    )
    public GuiButton rlmixins_otgOTGGuiPresetList_initGui_4(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        int maxBtnWidth = 330;
        int btnWidth = this.width - this.leftMargin - this.rightMargin;
        btnWidth = Math.min(btnWidth, maxBtnWidth);
        return new GuiButton(buttonId, (this.width - btnWidth)/2, y, btnWidth, heightIn, buttonText);
    }
    
    @Redirect(
            method = "drawScreen",
            at = @At(value = "INVOKE", target = "Lcom/pg85/otg/forge/gui/presets/OTGGuiPresetList;drawString(Lnet/minecraft/client/gui/FontRenderer;Ljava/lang/String;III)V")
    )
    public void rlmixins_otgOTGGuiPresetList_drawScreen(OTGGuiPresetList instance, FontRenderer fontRenderer, String s, int x, int y, int color) {
        //noop
    }
    
    @Inject(
            method = "wikiLinkClicked",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void rlmixins_otgOTGGuiPresetList_wikiLinkClicked(int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}