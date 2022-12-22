package rlmixins.wrapper;

import com.mujmajnkraft.bettersurvival.init.ModPotions;
import net.minecraft.potion.Potion;

public abstract class BetterSurvivalWrapper {
    public static Potion getAntiwarp() { return ModPotions.antiwarp; }
}