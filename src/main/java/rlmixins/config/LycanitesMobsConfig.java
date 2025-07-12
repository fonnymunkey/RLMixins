package rlmixins.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Level;
import rlmixins.RLMixins;
import rlmixins.util.ModLoadedUtil;

import java.util.ArrayList;
import java.util.List;

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

	@Config.Comment("Allows Lycanites Equipment to be enchanted with all Weapon type enchantments except Sweeping Edge.\n" +
			"Additionally allows Efficiency as the only tool enchantment.\n" +
			"Breakable type enchantments, such as Unbreaking and Mending, are not allowed.")
	@Config.Name("Enchantable Lycanites Equipment (LycanitesMobs)")
	@Config.RequiresMcRestart
	@MixinConfig.MixinToggle(lateMixin = "mixins.rlmixins.late.lycanitesmobs.equipmentenchantments.json", defaultValue = false)
	@MixinConfig.CompatHandling(
			modid = ModLoadedUtil.LycanitesMobs_MODID,
			desired = true,
			reason = "Requires mod to properly function"
	)
	public boolean equipmentEnchantments = false;

	@Config.Comment("Minimum level all parts of equipment must be in order to apply enchantments")
	@Config.Name("Enchantable Lycanites Equipment - Minimum Part Level")
	public int equipmentEnchantmentsMinLevelParts = 0;

	@Config.Comment("If enabled, the Equipment item shows a tooltip describing part level requirements. Else it is hidden and will only reveal enchantability when it is possible.")
	@Config.Name("Enchantable Lycanites Equipment - Minimum Part Level Tooltips")
	public boolean equipmentEnchantmentsMinLevelTooltips = true;

	@Config.Comment("If enabled, the Equipment Forge will prevent Equipment from being disassembled into its individual parts. Else disassembly can be done at the cost of clearing enchantments.")
	@Config.Name("Enchantable Lycanites Equipment - Prevent Disassembling")
	public boolean equipEnchDisassembleLock = true;

	@Config.Comment("Lycanites Equipment will not be able receive any enchantments in this list (of Resource Locations).\n" +
			"\tFormat: [namespace: path]")
	@Config.Name("Equipment Enchantment Blacklist")
	public String[] blacklistedEquipmentEnchants = {

	};

	private List<Enchantment> equipmentEnchantsBlacklist = null;

	public List<Enchantment> getEquipmentEnchantmentBlacklist() {
		if(equipmentEnchantsBlacklist == null) {
			equipmentEnchantsBlacklist = new ArrayList<>();
			for(String name : blacklistedEquipmentEnchants) {
				name = name.trim();
				if(name.isEmpty()) continue;
				Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(name));
				if(enchant == null) {
					RLMixins.LOGGER.log(Level.WARN, "Invalid enchantment " + name + " in blacklist for Lycanites Equipment");
					continue;
				}
				equipmentEnchantsBlacklist.add(enchant);
			}
		}
		return equipmentEnchantsBlacklist;
	}

	public void refreshConfig() {
		this.equipmentEnchantsBlacklist = null;
	}
}