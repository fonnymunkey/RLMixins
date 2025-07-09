package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;

@MixinConfig(name = RLMixins.MODID)
public class VanillaConfig {
	
	@Config.Comment("Skips BlockConcretePowder from running onBlockAdded during worldgen for performance")
	@Config.Name("ConcretePowder Skip OnBlockAdded (Vanilla)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(earlyMixin = "mixins.rlmixins.early.vanilla.concrete.json", defaultValue = false)
	public boolean concretePowderSkipOnBlockAdded = false;
}