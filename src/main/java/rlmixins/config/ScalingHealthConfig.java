package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class ScalingHealthConfig {
	
	@Config.Comment("Attempts to fix a desync caused by ScalingHealth when a mob dies in the same tick it is spawned")
	@Config.Name("Death Health Desync (ScalingHealth)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.scalinghealth.desync.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.ScalingHealth_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean deathHealthDesync = false;
	
	@Config.Comment("Fixes the bandaged effect not having an icon")
	@Config.Name("Bandaged Icon Fix (ScalingHealth)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.scalinghealth.icon.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.ScalingHealth_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean bandagedIconFix = false;
}