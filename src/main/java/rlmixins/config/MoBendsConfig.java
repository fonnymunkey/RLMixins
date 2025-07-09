package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class MoBendsConfig {
	
	@Config.Comment("Disables online checks that can cause the game to freeze on loading")
	@Config.Name("Disable Online Checks (MoBends)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.mobends.offline.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.MoBends_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean disableOnlineChecks = false;
}