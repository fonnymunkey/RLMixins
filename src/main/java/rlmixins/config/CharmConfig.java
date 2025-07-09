package rlmixins.config;

import net.minecraftforge.common.config.Config;

public class CharmConfig {
	
	@Config.Comment("Adds and registers the Cleansing Talisman, a recipe for crafting a Curse Break book, and the Curse Break potion")
	@Config.Name("Cleansing Talisman (Charm)")
	@Config.RequiresMcRestart
	public boolean cleansingTalisman = false;
}