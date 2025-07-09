package rlmixins.config;

import net.minecraftforge.common.config.Config;

public class RLArtifactsConfig {
	
	@Config.Comment("Makes the Antidote Vessel additionally take effect on potion applications")
	@Config.Name("Antidote Vessel Enhancement")
	@Config.RequiresMcRestart
	public boolean antidoteVesselEnhancement = false;
}