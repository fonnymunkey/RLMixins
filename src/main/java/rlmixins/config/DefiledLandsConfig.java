package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.config.Config;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class DefiledLandsConfig {
	
	@Config.Comment("Replaces the effect from the Scarlite Sword with a config defined effect")
	@Config.Name("Scarlite Sword Config Effect (DefiledLands)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.defiledlands.swordconfig.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.DefiledLands_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean scarliteSwordConfigEffect = false;
	
	@Config.Comment("Effect given by the Scarlite Sword when hitting an entity" + "\n" +
			"Requires \"Scarlite Sword Config Effect (DefiledLands)\" enabled")
	@Config.Name("Scarlite Sword Effect")
	public String scarliteSwordEffect = "lycanitesmobs:leech";
	
	@Config.Comment("Duration of effect given by the Scarlite Sword when hitting an entity" + "\n" +
			"Requires \"Scarlite Sword Config Effect (DefiledLands)\" enabled")
	@Config.Name("Scarlite Sword Effect Duration")
	@Config.RangeInt(min = 1, max = 1200)
	public int scarliteSwordDuration = 20;
	
	@Config.Comment("Amplifier of effect given by the Scarlite Sword when hitting an entity" + "\n" +
			"Requires \"Scarlite Sword Config Effect (DefiledLands)\" enabled")
	@Config.Name("Scarlite Sword Effect Amplifier")
	@Config.RangeInt(min = 0, max = 10)
	public int scarliteSwordAmplifier = 1;
	
	private Potion scarliteReplacementPotion = null;
	
	public Potion getScarliteReplacementPotion() {
		if(this.scarliteReplacementPotion == null) {
			this.scarliteReplacementPotion = Potion.getPotionFromResourceLocation(this.scarliteSwordEffect);
			if(this.scarliteReplacementPotion == null) {
				RLMixins.LOGGER.log(Level.ERROR, "Invalid Scarlite Sword Effect: " + this.scarliteSwordEffect);
				this.scarliteReplacementPotion = MobEffects.REGENERATION;
			}
		}
		return this.scarliteReplacementPotion;
	}
	
	public void refreshConfig() {
		this.scarliteReplacementPotion = null;
	}
}