package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class QuarkConfig {
	
	@Config.Comment("Makes feathers not passively drop from chickens if they're stone statues")
	@Config.Name("Stone Statue Chicken Feather Fix (Quark/InFRLCraft)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.quark.stonechicken.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.Quark_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.InFRLCraft_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean stoneStatueChickenFeatherFix = false;
	
	@Config.Comment("Enables equipping hats as baubles")
	@Config.Name("Hat Baubles (Quark/Baubles)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.quark.hatbaubles.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.Quark_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.Baubles_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean hatBaubles = false;
	
	@Config.Comment("Fixes hat rendering when using MoBends")
	@Config.Name("Hat MoBends Compat (Quark/MoBends)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.quark.hatmobends.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.Quark_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.MoBends_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean hatMoBendsCompat = false;
	
	@Config.Comment("Adds passive looting 1 when wearing the pirate hat")
	@Config.Name("Pirate Hat Looting")
	@Config.RequiresMcRestart
	public boolean pirateHatLooting = false;
}