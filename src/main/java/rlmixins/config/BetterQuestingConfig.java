package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class BetterQuestingConfig {
	
	@Config.Comment("Replaces the advancements tab button in the escape menu with a button for the quest menu")
	@Config.Name("Advancement Tab Quest Replacement (BetterQuesting)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(earlyMixin = "mixins.rlmixins.early.betterquesting.button.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.BetterQuesting_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean advancementTabQuestReplacement = false;
}