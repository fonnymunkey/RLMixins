package rlmixins.handlers;

import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import net.minecraft.enchantment.Enchantment;

public abstract class SMEHandler {
    public static Enchantment getEnchantmentCriticalStrike() {
        return Smc_030.CriticalStrike;
    }
}