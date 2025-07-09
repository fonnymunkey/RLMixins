package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class LycanitesMobsConfig {
	
	@Config.Comment("Stops mobs from attempting to target mobs that are stone statues, or tagged with NoAI")
	@Config.Name("Mob Targeting Fix (LycanitesMobs/InFRLCraft)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.lycanitesmobs.stonetarget.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.LycanitesMobs_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.InFRLCraft_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean mobTargetingFix = false;
}