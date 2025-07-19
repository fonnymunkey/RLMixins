package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import rlmixins.RLMixins;

import java.util.ArrayList;
import java.util.List;

@MixinConfig(name = RLMixins.MODID)
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
	
	@Config.Comment("Allows for defining effects to affect player movement inputs to simulate stumbling, such as nausea")
	@Config.Name("Player Nausea Movement Effects")
	@Config.RequiresMcRestart
	public boolean playerNauseaMovementEffects = false;
	
	@Config.Comment("Allows for defining effects to affect mob movement to simulate stumbling, such as nausea")
	@Config.Name("Mob Nausea Movement Effects")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(earlyMixin = "mixins.rlmixins.early.rlmixins.mobnausea.json", defaultValue = false)
	public boolean mobNauseaMovementEffects = false;
	
	@Config.Comment("Effects that should affect movement to simulate stumbling")
	@Config.Name("Nausea Movement Effects List")
	public String[] nauseaMovementEffectsList = { "minecraft:nausea", "minecraft:blindness", "rustic:tipsy" };
	
	private List<Potion> nauseaMovementPotionSet = null;
	
	public List<Potion> getNauseaMovementPotions() {
		if(this.nauseaMovementPotionSet == null) {
			this.nauseaMovementPotionSet = new ArrayList<>();
			for(String name : this.nauseaMovementEffectsList) {
				Potion potion = ForgeRegistries.POTIONS.getValue(new ResourceLocation(name));
				if(potion != null) this.nauseaMovementPotionSet.add(potion);
			}
		}
		return this.nauseaMovementPotionSet;
	}
	
	public void refreshConfig() {
		this.nauseaMovementPotionSet = null;
	}
}