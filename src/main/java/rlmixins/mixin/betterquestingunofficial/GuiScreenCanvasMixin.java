package rlmixins.mixin.betterquestingunofficial;

import betterquesting.api.client.gui.misc.IVolatileScreen;
import betterquesting.api2.client.gui.GuiScreenCanvas;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import betterquesting.api2.client.gui.popups.PopChoice;
import betterquesting.api2.client.gui.themes.presets.PresetIcon;
import betterquesting.api2.utils.QuestTranslation;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nonnull;

@Mixin(GuiScreenCanvas.class)
public abstract class GuiScreenCanvasMixin extends GuiScreen {

    @Shadow(remap = false) public abstract boolean onKeyTyped(char c, int keycode);

    @Shadow(remap = false) private boolean isVolatile;

    @Shadow(remap = false) public abstract void openPopup(@Nonnull IGuiPanel panel);

    @Shadow(remap = false) protected abstract void confirmClose(int id);

    /**
     * @author fonnymunkey
     * @reason Revert BQU's modified keyTyped to the original, to allow BQUTweaker to handle backspace instead
     */
    @Overwrite
    public void keyTyped(char c, int keyCode) {
        if (keyCode != 1) {
            this.onKeyTyped(c, keyCode);
        } else {
            if (!this.isVolatile && !(this instanceof IVolatileScreen)) {
                this.mc.displayGuiScreen((GuiScreen)null);
                if (this.mc.currentScreen == null) {
                    this.mc.setIngameFocus();
                }
            } else {
                this.openPopup(new PopChoice(QuestTranslation.translate("betterquesting.gui.closing_warning", new Object[0]) + "\n\n" + QuestTranslation.translate("betterquesting.gui.closing_confirm", new Object[0]), PresetIcon.ICON_NOTICE.getTexture(), this::confirmClose, new String[]{QuestTranslation.translate("gui.yes", new Object[0]), QuestTranslation.translate("gui.no", new Object[0])}));
            }
        }
    }
}