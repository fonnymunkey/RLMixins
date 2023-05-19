package rlmixins.mixin.otg;

import com.pg85.otg.forge.gui.mainmenu.OTGGuiWorldSelection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OTGGuiWorldSelection.class)
public abstract class OTGGuiWorldSelectionMixin extends GuiScreen {

    @Shadow(remap = false) private GuiButton selectButton;
    @Shadow(remap = false) private GuiButton deleteButton;

    /**
     * @author fonnymunkey
     * @reason Modify buttons to optimize options for clarity
     */
    @Overwrite(remap = false)
    private void postInit() {
        int margin = 4;
        int marginFromBottom = this.height - 54;
        int buttonHeight = 20;
        int uiWidth = 357;
        int marginFromLeft = Math.round((float)(this.width - uiWidth) / 2.0F);
        int row1Width = 3;
        int row1ButtonWidth = (int)Math.floor((double)((float)(uiWidth - (row1Width - 1) * margin) / (float)row1Width));
        int row1LeftOver = uiWidth - (row1ButtonWidth * row1Width + margin * (row1Width - 1));
        int row2Width = 2;
        int row2ButtonWidth = (int)Math.floor((double)((float)(uiWidth - (row2Width - 1) * margin) / (float)row2Width));
        int row2LeftOver = uiWidth - (row2ButtonWidth * row2Width + margin * (row2Width - 1));

        this.selectButton = this.addButton(
                new GuiButton(
                        1,
                        marginFromLeft,
                        marginFromBottom,
                        row2ButtonWidth,
                        buttonHeight,
                        I18n.format("selectWorld.select"))
        );
        this.addButton(
                new GuiButton(
                        3,
                        marginFromLeft + row2ButtonWidth + margin,
                        marginFromBottom,
                        row2ButtonWidth + row2LeftOver,
                        buttonHeight,
                        I18n.format("selectWorld.dregoraCreate")
                )
        );
        this.deleteButton = this.addButton(
                new GuiButton(
                        2,
                        marginFromLeft,
                        marginFromBottom + buttonHeight + margin,
                        row1ButtonWidth,
                        buttonHeight,
                        I18n.format("selectWorld.delete")
                )
        );
        this.addButton(
                new GuiButton(
                        4,
                        marginFromLeft + row1ButtonWidth + margin,
                        marginFromBottom + buttonHeight + margin,
                        row1ButtonWidth,
                        buttonHeight,
                        I18n.format("selectWorld.vanillaCreate")
                )
        );
        this.addButton(
                new GuiButton(
                        0,
                        marginFromLeft + row1ButtonWidth + margin + row1ButtonWidth + margin,
                        marginFromBottom + buttonHeight + margin,
                        row1ButtonWidth + row1LeftOver,
                        buttonHeight,
                        I18n.format("gui.cancel")
                )
        );

        this.selectButton.enabled = false;
        this.deleteButton.enabled = false;
    }
}