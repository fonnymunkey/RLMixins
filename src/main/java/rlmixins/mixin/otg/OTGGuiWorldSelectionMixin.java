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
                        row2ButtonWidth,
                        buttonHeight,
                        I18n.format("selectWorld.delete")
                )
        );
        this.addButton(
                new GuiButton(
                        0,
                        marginFromLeft + row2ButtonWidth + margin,
                        marginFromBottom + buttonHeight + margin,
                        row2ButtonWidth + row2LeftOver,
                        buttonHeight,
                        I18n.format("gui.cancel")
                )
        );

        this.selectButton.enabled = false;
        this.deleteButton.enabled = false;
    }
}