package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class BetterFoliageConfig {
	
	@Config.Comment("Temporarily disables chunk animation when chunks are forced to refresh using BetterFoliage to stop xray abuse" + "\n" +
			"Requires \"Chunk Animation XRay Patch (ChunkAnimator)\" enabled")
	@Config.Name("BetterFoliage Chunk Animation XRay Patch (BetterFoliage/ChunkAnimator)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.betterfoliage.chunkanimator.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.BetterFoliage_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.ChunkAnimator_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean betterFoliageChunkAnimationXRayPatch = false;
}