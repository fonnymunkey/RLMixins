package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class JEIConfig {
	
	@Config.Comment("Reverts the behavior of JEI moving bookmarks out of the config folder")
	@Config.Name("Revert Bookmark Location Change (JEI)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.jei.bookmark.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.JEI_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean revertBookmarkLocationChange = false;
	
	@Config.Comment("Suppresses JEI outputting recipe errors related to VariedCommodities")
	@Config.Name("Suppress VariedCommodities Errors (JEI/VariedCommodities)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.jei.vcerror.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.JEI_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean suppressVCErrors = false;
}