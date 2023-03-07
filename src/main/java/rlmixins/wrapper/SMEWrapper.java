package rlmixins.wrapper;

import com.Shultrea.Rin.Enchantments_Sector.Smc_010;
import com.Shultrea.Rin.Enchantments_Sector.Smc_020;
import com.Shultrea.Rin.Enchantments_Sector.Smc_030;
import com.Shultrea.Rin.Enchantments_Sector.Smc_040;
import com.Shultrea.Rin.Enum.EnumList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.fml.common.Loader;

public abstract class SMEWrapper {

    public static Enchantment getSwifterSlashes() { return Smc_010.SwifterSlashes; }
    public static Enchantment getHeavyWeight() { return Smc_010.HeavyWeight; }
    public static Enchantment getRusted() { return Smc_010.Rusted; }
    public static Enchantment getLesserFireAspect() { return Smc_040.LesserFireAspect; }
    public static Enchantment getAdvancedFireAspect() { return Smc_040.AdvancedFireAspect; }
    public static Enchantment getSupremeFireAspect() { return Smc_040.SupremeFireAspect; }
    public static Enchantment getAdvancedKnockback() { return Smc_020.AdvancedKnockback; }
    public static Enchantment getAdept() { return Smc_040.Adept; }
    public static Enchantment getAdvancedLooting() { return Smc_030.AdvancedLooting; }

    public static  EnumEnchantmentType getCombatAxeType() { return EnumList.COMBAT_AXE; }
    public static EnumEnchantmentType getCombatType() { return EnumList.COMBAT; }
}