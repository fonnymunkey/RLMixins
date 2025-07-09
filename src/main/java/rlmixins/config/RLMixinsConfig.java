package rlmixins.config;

import net.minecraftforge.common.config.Config;

public class RLMixinsConfig {
	
	@Config.Comment("Adds and registers Steel armor with custom models")
	@Config.Name("Steel Armor")
	@Config.RequiresMcRestart
	public boolean steelArmor = false;
	
	@Config.Comment("Adds and registers Scarlite armor with custom models")
	@Config.Name("Scarlite Armor")
	@Config.RequiresMcRestart
	public boolean scarliteArmor = false;
	
	@Config.Comment("Adds and registers the Antimagic Talisman, and a recipe for crafting it with enchanted items")
	@Config.Name("Antimagic Talisman")
	@Config.RequiresMcRestart
	public boolean antimagicTalisman = false;
	
	@Config.Comment("Adds and registers the Lesser Fire Resistance potion effect")
	@Config.Name("Lesser Fire Resistance Potion Effect")
	@Config.RequiresMcRestart
	public boolean lesserFireResistancePotionEffect = false;
	
	@Config.Comment("Adds and registers the Cow potion effect")
	@Config.Name("Cow Potion Effect")
	@Config.RequiresMcRestart
	public boolean cowPotionEffect = false;
	
	@Config.Comment("Adds and registers additional useful loot functions for json loot tables")
	@Config.Name("Additional Loot Functions")
	@Config.RequiresMcRestart
	public boolean additionalLootFunctions = false;
}