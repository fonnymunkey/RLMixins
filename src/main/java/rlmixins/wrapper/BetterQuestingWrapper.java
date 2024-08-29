package rlmixins.wrapper;

import betterquesting.api.storage.BQ_Settings;
import betterquesting.api2.client.gui.themes.gui_args.GArgsNone;
import betterquesting.api2.client.gui.themes.presets.PresetGUIs;
import betterquesting.client.gui2.GuiHome;
import betterquesting.client.themes.ThemeRegistry;
import net.minecraft.client.Minecraft;

public abstract class BetterQuestingWrapper {
	
	public static void displayQuestScreen(Minecraft mc) {
		if(BQ_Settings.useBookmark && GuiHome.bookmark != null) mc.displayGuiScreen(GuiHome.bookmark);
		else mc.displayGuiScreen(ThemeRegistry.INSTANCE.getGui(PresetGUIs.HOME, GArgsNone.NONE));
	}
}