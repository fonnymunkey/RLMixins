package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class EpicSiegeModConfig {
	
	@Config.Comment("Disables the digging AI for mobs that are not carrying a pickaxe and disables other griefing AI for performance")
	@Config.Name("Digging AI Patch (EpicSiegeMod)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.epicsiegemod.digging.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.EpicSiegeMod_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean diggingAIPatch = false;
}