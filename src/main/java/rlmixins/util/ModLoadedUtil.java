package rlmixins.util;

import net.minecraftforge.fml.common.Loader;

public abstract class ModLoadedUtil {
	
	private static Boolean betterSurvivalLoaded = null;
	private static Boolean spartanWeaponryLoaded = null;
	private static Boolean scalingHealthLoaded = null;
	private static Boolean infernalMobsLoaded = null;
	private static Boolean championsLoaded = null;
	private static Boolean soManyEnchantmentsLoaded = null;
	private static Boolean spartanFireLoaded = null;
	private static Boolean rlcombatLoaded = null;
	private static Boolean biomesOPlentyLoaded = null;
	
	public static boolean isBetterSurvivalLoaded() {
		if(betterSurvivalLoaded == null) betterSurvivalLoaded = Loader.isModLoaded("mujmajnkraftsbettersurvival");
		return betterSurvivalLoaded;
	}
	
	public static boolean isSpartanWeaponryLoaded() {
		if(spartanWeaponryLoaded == null) spartanWeaponryLoaded = Loader.isModLoaded("spartanweaponry");
		return spartanWeaponryLoaded;
	}
	
	public static boolean isScalingHealthLoaded() {
		if(scalingHealthLoaded == null) scalingHealthLoaded = Loader.isModLoaded("scalinghealth");
		return scalingHealthLoaded;
	}
	
	public static boolean isInfernalMobsLoaded() {
		if(infernalMobsLoaded == null) infernalMobsLoaded = Loader.isModLoaded("infernalmobs");
		return infernalMobsLoaded;
	}
	
	public static boolean isChampionsLoaded() {
		if(championsLoaded == null) championsLoaded = Loader.isModLoaded("champions");
		return championsLoaded;
	}
	
	public static boolean isSoManyEnchantmentsLoaded() {
		if(soManyEnchantmentsLoaded == null) soManyEnchantmentsLoaded = Loader.isModLoaded("somanyenchantments");
		return soManyEnchantmentsLoaded;
	}
	
	public static boolean isSpartanFireLoaded() {
		if(spartanFireLoaded == null) spartanFireLoaded = Loader.isModLoaded("spartanfire");
		return spartanFireLoaded;
	}
	
	public static boolean isRLCombatLoaded() {
		if(rlcombatLoaded == null) rlcombatLoaded = Loader.isModLoaded("bettercombatmod");
		return rlcombatLoaded;
	}

	public static boolean isBiomeOPlentyLoaded() {
		if(biomesOPlentyLoaded == null) biomesOPlentyLoaded = Loader.isModLoaded("biomesoplenty");
		return biomesOPlentyLoaded;
	}
}