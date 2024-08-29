package rlmixins.wrapper;

import bettercombat.mod.compat.EnchantCompatHandler;

public abstract class RLCombatWrapper {
	
	public static boolean isKnockbackOffhand() {
		return EnchantCompatHandler.knockbackFromOffhand;
	}
	
	public static boolean isFireAspectOffhand() {
		return EnchantCompatHandler.fireAspectFromOffhand;
	}
}