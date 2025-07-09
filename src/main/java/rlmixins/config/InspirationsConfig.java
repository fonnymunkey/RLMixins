package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class InspirationsConfig {
	
	@Config.Comment("Makes incorrectly mixing potions in a cauldron turn into a Mundane instead of a Thick potion")
	@Config.Name("Cauldron Failure Mundane (Inspirations)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.inspirations.mundane.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.Inspirations_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean cauldronFailureMundane = false;
}