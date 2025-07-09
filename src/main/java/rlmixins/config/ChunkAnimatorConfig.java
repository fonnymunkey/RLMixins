package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class ChunkAnimatorConfig {
	
	@Config.Comment("Temporarily disables chunk animation when chunks are forced to refresh to stop xray abuse")
	@Config.Name("Chunk Animation XRay Patch (ChunkAnimator)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(
			earlyMixin = "mixins.rlmixins.early.chunkanimator.xray.json",
			lateMixin = "mixins.rlmixins.late.chunkanimator.xray.json",
			defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.ChunkAnimator_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean chunkAnimationXRayPatch = false;
}