package rlmixins.wrapper;

import com.mujmajnkraft.bettersurvival.init.ModEnchantments;
import com.mujmajnkraft.bettersurvival.init.ModPotions;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.potion.Potion;

public abstract class BetterSurvivalWrapper {
    public static Potion getAntiwarp() { return ModPotions.antiwarp; }
    public static Enchantment getMultishot() { return ModEnchantments.multishot; }
    public static Enchantment getEducation() { return ModEnchantments.education; }
}