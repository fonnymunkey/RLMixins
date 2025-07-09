package rlmixins.config;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;

import java.util.HashSet;
import java.util.Set;

public class RLCombatConfig {
	
	@Config.Comment("Enables the Nether Bane weapon effect to deal bonus damage to nether mobs")
	@Config.Name("Enable Nether Bane")
	@Config.RequiresMcRestart
	public boolean enableNetherBane = false;
	
	@Config.Comment("List of mobs to be classed as nether mobs for the Nether Bane effect")
	@Config.Name("Nether Bane Mob List")
	public String[] netherBaneMobs = {
			"minecraft:wither_skeleton",
			"minecraft:zombie_pigman",
			"minecraft:blaze",
			"minecraft:magma_cube",
			"minecraft:wither"};
	
	@Config.Comment("List of weapons to have the Nether Bane effect")
	@Config.Name("Nether Bane Weapon List")
	public String[] netherBaneWeapons = {""};
	
	@Config.Comment("If true, Nether Bane effect will multiply damage, if false, additive")
	@Config.Name("Nether Bane Multiply/Add")
	public boolean netherBaneMultiply = false;
	
	@Config.Comment("Value to either multiply damage by or add to damage for the Nether Bane effect")
	@Config.Name("Nether Bane Damage Value")
	public double netherBaneValue = 4.0;
	
	private Set<ResourceLocation> netherBaneMobsSet = null;
	private Set<ResourceLocation> netherBaneWeaponsSet = null;
	
	public Set<ResourceLocation> getNetherBaneMobs() {
		if(this.netherBaneMobsSet == null) {
			this.netherBaneMobsSet = new HashSet<>();
			for(String name : this.netherBaneMobs) {
				this.netherBaneMobsSet.add(new ResourceLocation(name));
			}
		}
		return this.netherBaneMobsSet;
	}
	
	public Set<ResourceLocation> getNetherBaneWeapons() {
		if(this.netherBaneWeaponsSet == null) {
			this.netherBaneWeaponsSet = new HashSet<>();
			for(String name : this.netherBaneWeapons) {
				this.netherBaneWeaponsSet.add(new ResourceLocation(name));
			}
		}
		return this.netherBaneWeaponsSet;
	}
	
	public void refreshConfig() {
		this.netherBaneMobsSet = null;
		this.netherBaneWeaponsSet = null;
	}
}