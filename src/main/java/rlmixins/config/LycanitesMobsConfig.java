package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

import java.util.HashSet;
import java.util.Set;

@MixinConfig(name = RLMixins.MODID)
public class LycanitesMobsConfig {
	
	@Config.Comment("Stops mobs from attempting to target mobs that are stone statues, or tagged with NoAI")
	@Config.Name("Mob Targeting Fix (LycanitesMobs/InFRLCraft)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.lycanitesmobs.stonetarget.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.LycanitesMobs_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.InFRLCraft_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean mobTargetingFix = false;

	@Config.Comment("Allows forged equipment to be enchanted" + "\n" +
			"Allows all WEAPON enchantments except Sweeping Edge" + "\n" +
			"Allows Efficiency as the only TOOL enchantment" + "\n" +
			"Durability enchantments, such as Unbreaking and Mending, are not allowed")
	@Config.Name("Enchant Forged Equipment (LycanitesMobs)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.lycanitesmobs.equipmentenchantments.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.LycanitesMobs_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean enchantForgedEquipment = false;

	@Config.Comment("Minimum level all parts of equipment must be in order to enchant")
	@Config.Name("Minimum Part Level For Enchants")
	public int minimumPartLevelForEnchants = 0;

	@Config.Comment("If equipment should give a warning tooltip if it has parts that are not high enough level to be enchanted")
	@Config.Name("Minimum Part Level Tooltip")
	public boolean minimumPartLevelTooltip = true;

	@Config.Comment("If equipment should be prevented from being disassembled if it is enchanted")
	@Config.Name("Enchants Prevent Disassembling")
	public boolean enchantsPreventDisassembling = true;

	@Config.Comment("List of enchants to additionally blacklist from being applicable to equipment" + "\n" +
			"Format: modid:path")
	@Config.Name("Equipment Enchantment Blacklist")
	public String[] equipmentEnchantmentBlacklist = { };

	private Set<Enchantment> equipmentEnchantmentBlacklistSet = null;

	public Set<Enchantment> getEquipmentEnchantmentBlacklist() {
		if(this.equipmentEnchantmentBlacklistSet == null) {
			this.equipmentEnchantmentBlacklistSet = new HashSet<>();
			for(String name : this.equipmentEnchantmentBlacklist) {
				name = name.trim();
				if(name.isEmpty()) continue;
				Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(name));
				if(enchant == null) {
					RLMixins.LOGGER.log(Level.WARN, "Invalid enchantment " + name + " in blacklist for Lycanite equipment enchanting");
					continue;
				}
				this.equipmentEnchantmentBlacklistSet.add(enchant);
			}
		}
		return this.equipmentEnchantmentBlacklistSet;
	}

	public void refreshConfig() {
		this.equipmentEnchantmentBlacklistSet = null;
	}
}