package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.config.Config;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

@MixinConfig(name = RLMixins.MODID)
public class SRParasitesConfig {
	
	@Config.Comment("Replaces Living and Sentient armor models with custom models")
	@Config.Name("Replace Parasite Armor Models (SRParasites)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.srparasites.armormodel.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.SRParasites_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean replaceParasiteArmorModels = false;
	
	@Config.Comment("Makes Parasite armor cure/lower the effect of Fear")
	@Config.Name("Parasite Armor Fear Cure")
	@Config.RequiresMcRestart
	public boolean armorFearCure = false;
	
	@Config.Comment("Maximum amplifier of Fear while wearing Parasite armor (-1 = cures it)" + "\n" +
			"Requires \"Parasite Armor Fear Cure\" enabled")
	@Config.Name("Parasite Armor Fear Cure Max Amplifier")
	@Config.RangeInt(min = -1, max = 10)
	public int armorFearCureMax = 1;
	
	@Config.Comment("Makes Parasite armor cure/lower the effect of Viral")
	@Config.Name("Parasite Armor Viral Cure")
	@Config.RequiresMcRestart
	public boolean armorViralCure = false;
	
	@Config.Comment("Maximum amplifier of Viral while wearing Parasite armor (-1 = cures it)" + "\n" +
			"Requires \"Parasite Armor Viral Cure\" enabled")
	@Config.Name("Parasite Armor Viral Cure Max Amplifier")
	@Config.RangeInt(min = -1, max = 10)
	public int armorViralCureMax = 2;
	
	@Config.Comment("Makes callable horses register as dead when converted to parasites")
	@Config.Name("Horse Death On Conversion (SRParasites/CallableHorses)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.srparasites.horse.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.SRParasites_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.CallableHorses_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean horseDeathOnConversion = false;
	
	@Config.Comment("Rehandles and rebalances the scythe and maul AOE")
	@Config.Name("Rehandle AOE Weapons (SRParasites/RLCombat)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.srparasites.aoeweapon.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.SRParasites_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.RLCombat_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean rehandleAOEWeapons = false;
	
	@Config.Comment("Allows for replacing the cleaver effect with a config defined effect")
	@Config.Name("Modify Cleaver Effect (SRParasites)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.srparasites.cleaver.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.SRParasites_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean modifyCleaverEffect = false;
	
	@Config.Comment("Effect given by the cleaver when hitting an entity" + "\n" +
			"Requires \"Modify Cleaver Effect (SRParasites)\" enabled")
	@Config.Name("Cleaver Effect")
	public String cleaverEffect = "potioncore:vulnerable";
	
	@Config.Comment("Amplifier given by living cleaver when hitting an entity" + "\n" +
			"Requires \"Modify Cleaver Effect (SRParasites)\" enabled")
	@Config.Name("Living Cleaver Amplifier")
	@Config.RangeInt(min = 0, max = 10)
	public int livingCleaverAmplifier = 0;
	
	@Config.Comment("Amplifier given by sentient cleaver when hitting an entity" + "\n" +
			"Requires \"Modify Cleaver Effect (SRParasites)\" enabled")
	@Config.Name("Sentient Cleaver Amplifier")
	@Config.RangeInt(min = 0, max = 10)
	public int sentientCleaverAmplifier = 1;
	
	private Potion cleaverEffectPotion = null;
	
	public Potion getCleaverEffect() {
		if(this.cleaverEffectPotion == null) {
			this.cleaverEffectPotion = Potion.getPotionFromResourceLocation(this.cleaverEffect);
			if(this.cleaverEffectPotion == null) {
				RLMixins.LOGGER.log(Level.ERROR, "Invalid Cleaver Effect: " + this.cleaverEffect);
				this.cleaverEffectPotion = MobEffects.POISON;
			}
		}
		return this.cleaverEffectPotion;
	}
	
	public void refreshConfig() {
		this.cleaverEffectPotion = null;
	}
}