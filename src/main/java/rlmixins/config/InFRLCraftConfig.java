package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class InFRLCraftConfig {
	
	@Config.Comment("Stops dragons that are not ticking on the edge of render distance from receiving damage")
	@Config.Name("Dragon Ticking Abuse Fix (InFRLCraft)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.infrlcraft.dragontick.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.InFRLCraft_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean dragonTickingAbuseFix = false;
	
	@Config.Comment("Makes callable horses register as dead when turned to stone")
	@Config.Name("Horse Death On Stoning (InFRLCraft/CallableHorses)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.infrlcraft.horse.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.InFRLCraft_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.CallableHorses_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean horseDeathOnStoning = false;
}