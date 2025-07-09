package rlmixins.wrapper;

import net.minecraft.enchantment.Enchantment;
import svenhjol.charm.enchanting.feature.CurseBreak;

public abstract class CharmWrapper {
	
	public static Enchantment getCurseBreak() {
		return CurseBreak.enchantment;
	}
}