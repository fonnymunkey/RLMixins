package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class CharmConfig {
	
	@Config.Comment("Adds and registers the Cleansing Talisman, a recipe for crafting a Curse Break book, and the Curse Break potion")
	@Config.Name("Cleansing Talisman (Charm)")
	@Config.RequiresMcRestart
	public boolean cleansingTalisman = false;
	
	@Config.Comment("Makes the ender sight potion use the vanilla enderman invert shader rather than a custom slightly-off shader")
	@Config.Name("Ender Sight Render Fix (Charm)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.charm.endersight.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.Charm_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean enderSightRenderFix = false;
}